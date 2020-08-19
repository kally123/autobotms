package com.autobot.util;

import com.autobot.domain.EntityDetails;
import com.autobot.domain.EntityRelationDetails;
import com.autobot.domain.EntityRelationField;
import com.autobot.domain.Field;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;
import org.codehaus.plexus.util.StringUtils;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static com.autobot.constants.AutobotConstants.EMPTY_SPACE_CHAR;
import static com.autobot.constants.AutobotConstants.NEW_LINE_CHAR;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class AutobotUtil {

    @Autowired
    private Environment environment;

    /**
     * This helps to do post activities on the generated pom like correcting the
     * versions of dependencies and adding extra dependencies
     *
     * @param pomFile
     * @throws JAXBException
     * @throws FileNotFoundException
     * @throws IOException
     * @throws XmlPullParserException
     */
    public org.apache.maven.model.Model pomCleanup(File pomFile)
            throws JAXBException, FileNotFoundException, IOException, XmlPullParserException {
        MavenXpp3Reader mavenreader = new MavenXpp3Reader();
        FileReader f = new FileReader(pomFile);
        org.apache.maven.model.Model model = mavenreader.read(f);

        Properties properties = model.getProperties();
        properties.setProperty("aspectj.plugin.version", "1.9");
        model.setProperties(properties);

        Dependency dependency = new Dependency();
        dependency.setGroupId("ch.qos.logback");
        dependency.setArtifactId("logback-core");
        dependency.setVersion("1.1.11");

        model.addDependency(dependency);

        MavenXpp3Writer mavenXpp3Writer = new MavenXpp3Writer();
        Writer fileWriter = new FileWriter(pomFile);
        mavenXpp3Writer.write(fileWriter, model);
        fileWriter.close();
        f.close();

        return model;
    }

    /**
     * Helps to open the project url to run automatically
     *
     * @param port
     */
    public void openAppUrl(String port) {
        String url = "http://localhost:" + port;
        String os = System.getProperty("os.name").toLowerCase();
        Runtime rt = Runtime.getRuntime();

        try {

            if (os.indexOf("win") >= 0) {
                rt.exec("rundll32 url.dll,FileProtocolHandler " + url);
            } else if (os.indexOf("mac") >= 0) {
                try {
                    rt.exec("open " + url);
                } catch (IOException e) {
                    System.out.println(e);
                }
            } else if (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0) {

                // Do a best guess on unix until we get a platform independent
                // way
                // Build a list of browsers to try, in this order.
                String[] browsers = {"epiphany", "firefox", "mozilla", "konqueror", "netscape", "opera", "links",
                        "lynx"};

                // Build a command string which looks like "browser1 "url" ||
                // browser2 "url" ||..."
                StringBuffer cmd = new StringBuffer();
                for (int i = 0; i < browsers.length; i++)
                    cmd.append((i == 0 ? "" : " || ") + browsers[i] + " \"" + url + "\" ");

                rt.exec(new String[]{"sh", "-c", cmd.toString()});

            } else {
                return;
            }
        } catch (Exception e) {
            return;
        }
        return;
    }

    /**
     * Initialises the Entity details bean
     *
     * @param modelAndView
     */
    public EntityDetails initializeEntityDetails() {
        EntityDetails entityDetails = new EntityDetails();
        List<Field> fields = new ArrayList<Field>();
        fields.add(new Field());
        entityDetails.setFields(fields);
        return entityDetails;
    }

    /**
     * Initialises the Entity Relations bean
     *
     * @param modelAndView
     */
    public EntityRelationDetails initializeEntityRelationsForm() {
        EntityRelationDetails entityRelationsDetailsForm = new EntityRelationDetails();
        List<EntityRelationField> fields = new ArrayList<EntityRelationField>();
        fields.add(new EntityRelationField());
        entityRelationsDetailsForm.setFields(fields);
        return entityRelationsDetailsForm;
    }

    /**
     * Generates a Dummy Entity Details objects for quick testing
     *
     * @param daoClassNameList
     */
    public void createDummyEntities(List<String> daoClassNameList) {
        EntityDetails details = new EntityDetails();
        details.setEntityName("Address");
        details.setPackageName("model");
        List<Field> fields = new ArrayList<Field>();
        details.setFields(fields);
        Field f = new Field();
        fields.add(f);
        f.setFieldName("street");
        f.setFieldType("string");

        Field f1 = new Field();
        fields.add(f1);
        f1.setFieldName("city");
        f1.setFieldType("string");

        daoClassNameList.add(details.getPackageName() + "." + details.getEntityName());

        EntityDetails details1 = new EntityDetails();
        details1.setEntityName("Customer");
        details1.setPackageName("model");
        List<Field> fields1 = new ArrayList<Field>();
        details1.setFields(fields1);
        Field f2 = new Field();
        fields1.add(f2);
        f2.setFieldName("firstName");
        f2.setFieldType("string");

        Field f3 = new Field();
        fields1.add(f3);
        f3.setFieldName("lastName");
        f3.setFieldType("string");

        daoClassNameList.add(details1.getPackageName() + "." + details1.getEntityName());
    }

    public String createAppendStringText(boolean prefixNewLineChar, String... envProperties) {
        String envProps = "";
        for (String envProp : envProperties) {
            envProps += environment.getProperty(envProp) + NEW_LINE_CHAR;
        }
        return (prefixNewLineChar ? NEW_LINE_CHAR : "") + envProps;
    }

    public String createAppendString(String envProperty, String appendValue, boolean prefixNewLineChar) {
        if (StringUtils.isNotEmpty(appendValue)) {
            return (prefixNewLineChar ? NEW_LINE_CHAR : "") + environment.getProperty(envProperty) + EMPTY_SPACE_CHAR
                    + appendValue + NEW_LINE_CHAR;
        }
        return "";
    }

    public String createAppendString(String appendValue, boolean prefixNewLineChar, String... envProperties) {
        if (StringUtils.isNotEmpty(appendValue)) {
            String envProps = "";
            for (String envProp : envProperties) {
                envProps += environment.getProperty(envProp) + EMPTY_SPACE_CHAR;
            }
            return (prefixNewLineChar ? NEW_LINE_CHAR : "") + envProps + appendValue + NEW_LINE_CHAR;
        }
        return "";
    }

    /**
     * @param appendValue
     * @param prefixNewLineChar
     * @param suffixNewLineChar
     * @param envProperties
     * @return
     */
    public String createAppendString(String appendValue, boolean prefixNewLineChar, boolean suffixNewLineChar,
                                     String... envProperties) {
        if (StringUtils.isNotEmpty(appendValue)) {
            String envProps = "";
            for (String envProp : envProperties) {
                envProps += environment.getProperty(envProp) + EMPTY_SPACE_CHAR;
            }
            return (prefixNewLineChar ? NEW_LINE_CHAR : "") + envProps + appendValue + EMPTY_SPACE_CHAR
                    + (suffixNewLineChar ? NEW_LINE_CHAR : "");
        }
        return "";
    }

    public String createFullStringValues(String[] appendValues, String... envProperties) {
        String value = "";
        for (int i = 0; i < envProperties.length; i++) {
            if (StringUtils.isNotEmpty(appendValues[i])) {
                value += environment.getProperty(envProperties[i]) + EMPTY_SPACE_CHAR + appendValues[i]
                        + EMPTY_SPACE_CHAR;
            }
        }

        return value;
    }
}
