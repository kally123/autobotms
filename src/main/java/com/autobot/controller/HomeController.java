package com.autobot.controller;

import com.autobot.constants.AutobotConstants;
import com.autobot.domain.*;
import com.autobot.util.AutobotUtil;
import org.apache.commons.io.FileUtils;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.util.*;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
    private static AutoBot autobot = new AutoBot();
    private static Set<String> daoClassNameList = new HashSet<String>();
    private static Set<String> enumList = new HashSet<String>();
    File rooFile = null;
    String rooBatPath;
    @Autowired
    private Environment environment;
    @Autowired
    private AutobotUtil creatorUtil;
    private Set<String> sampleProjectList = new HashSet<String>();

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView home(Locale locale, Model model) throws URISyntaxException {
        model.addAttribute("autoBotForm", autobot);
        Date date = new Date();
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

        String formattedDate = dateFormat.format(date);
        rooBatPath = getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
        model.addAttribute("serverTime", formattedDate);
        for (File file : new File(rooBatPath + "samples" + File.separator).listFiles()) {
            sampleProjectList.add(file.getName().replace(".roo", ""));
        }

        model.addAttribute("sampleProjectList", sampleProjectList);
        logger.info("Welcome to Autobot!");
        return new ModelAndView("home");
    }

    @RequestMapping(value = "/loadProject", method = RequestMethod.POST)
    public ModelAndView loadProject(@ModelAttribute("autoBotForm") AutoBot autoBotForm,
                                    BindingResult result) {
        ModelAndView modelAndView = new ModelAndView("home");
        autobot.setProjectDetails(autoBotForm.getProjectDetails());
        modelAndView.addObject("sampleProjectList", sampleProjectList);
        logger.info(autoBotForm.getProjectDetails().getProjectName() + " details are loaded..");
        return modelAndView;
    }

    @RequestMapping(value = "/projectDetails", method = RequestMethod.GET)
    public String viewProjectDetails(Map<String, Object> model) {
        model.put("projectDetailsForm", autobot.getProjectDetails());
        autobot = new AutoBot();
        daoClassNameList = new HashSet<String>();
        enumList = new HashSet<String>();
        return "projectDetails";
    }

    @RequestMapping(value = "/projectDetails", method = RequestMethod.POST)
    public ModelAndView projectDetails(@Valid @ModelAttribute("projectDetailsForm") ProjectDetails projectDetailsForm,
                                       BindingResult result, Map<String, Object> model) {
        if (result.hasErrors()) {
            model.put("projectDetailsForm", autobot.getProjectDetails());
            return new ModelAndView("projectDetails");
        }

        StringBuffer str = new StringBuffer(environment.getProperty("project.setup.createProject.text"));
        str.append(AutobotConstants.NEW_LINE_CHAR)
                .append(creatorUtil.createAppendString(projectDetailsForm.getPackageName(), false, false,
                        "project.setup.command"))
                .append(creatorUtil.createAppendString(projectDetailsForm.getProjectName(), false,
                        "project.setup.projectName"));
        autobot.setProjectDetails(projectDetailsForm);
        appendTORooFile(str.toString());
        logger.info(autobot.getProjectDetails().getProjectName() + " is created..");
        return new ModelAndView("redirect:databaseSetup");
    }

    @RequestMapping(value = "/databaseSetup", method = RequestMethod.GET)
    public String viewDatabaseSetup(Map<String, Object> model) {
        model.put("databaseSetupForm", autobot.getDatabaseSetup());
        model.put("projectName", autobot.getProjectDetails().getProjectName());
        return "databaseSetup";
    }

    @RequestMapping(value = "/databaseSetup", method = RequestMethod.POST)
    public ModelAndView databaseSetup(@Valid @ModelAttribute("databaseSetupForm") DatabaseSetup databaseSetupForm,
                                      BindingResult result, Map<String, Object> model) {
        if (result.hasErrors()) {
            return new ModelAndView("redirect:databaseSetup");
        }
        StringBuffer str = new StringBuffer(environment.getProperty("project.setup.setupJPA") + AutobotConstants.EMPTY_SPACE_CHAR);
        str.append(creatorUtil.createAppendString(databaseSetupForm.getDatabaseProvider(), false, false,
                "project.setup.provider"))
                .append(creatorUtil.createAppendString(databaseSetupForm.getDatabase(), false,
                        "project.setup.database"))
                .append(AutobotConstants.NEW_LINE_CHAR);
        autobot.setDatabaseSetup(databaseSetupForm);
        appendTORooFile(str.toString());
        model.put("projectName", autobot.getProjectDetails().getProjectName());
        logger.info(autobot.getProjectDetails().getProjectName() + " is added with Database details..");
        return new ModelAndView("redirect:entityDetails");
    }

    @RequestMapping(value = "/entityDetails", method = RequestMethod.GET)
    public ModelAndView viewEntityDetails(Map<String, Object> model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("entityDetailsForm", creatorUtil.initializeEntityDetails());
        modelAndView.addObject("entityRelationsForm", creatorUtil.initializeEntityRelationsForm());
        model.put("projectName", autobot.getProjectDetails().getProjectName());
        model.put("daoClassNameList", daoClassNameList);
        model.put("enumList", enumList);
        modelAndView.setViewName("entityDetails");
        return modelAndView;
    }

    @RequestMapping(value = "/entityDetails", method = RequestMethod.POST)
    public ModelAndView entityDetails(@Valid @ModelAttribute("entityDetailsForm") EntityDetails entityDetailsForm,
                                      BindingResult result, Map<String, Object> model) {
        ModelAndView mv = new ModelAndView("entityDetails");
        autobot.setEntityDetails(entityDetailsForm);
        if (result.hasErrors()) {
            mv.addObject("entityDetailsForm", autobot.getEntityDetails());
            mv.addObject("entityRelationsForm", autobot.getEntityRelationDetails());
            model.put("projectName", autobot.getProjectDetails().getProjectName());
            model.put("daoClassNameList", daoClassNameList);
            model.put("enumList", enumList);
            return mv;
        }

        StringBuffer str = new StringBuffer(environment.getProperty("entity.setup.setupEntity.text"));
        str.append(AutobotConstants.NEW_LINE_CHAR).append(AutobotConstants.FORWARD_SLASH_CHAR).append(AutobotConstants.EMPTY_SPACE_CHAR)
                .append(entityDetailsForm.getEntityName()).append(AutobotConstants.NEW_LINE_CHAR);
        String entityName = entityDetailsForm.getPackageName() + "." + entityDetailsForm.getEntityName();
        if (AutobotConstants.ENTITY.equals(entityDetailsForm.getEntityType())) {
            str.append(environment.getProperty("entity.setup.jpa")).append(AutobotConstants.EMPTY_SPACE_CHAR);
            daoClassNameList.add(entityName);
        } else if (AutobotConstants.ENUM.equals(entityDetailsForm.getEntityType())) {
            str.append(environment.getProperty("entity.setup.enum.type")).append(AutobotConstants.EMPTY_SPACE_CHAR);
            enumList.add(entityName);
        }
        str.append(environment.getProperty("entity.setup.class")).append(AutobotConstants.EMPTY_SPACE_CHAR)
                .append(environment.getProperty("entity.setup.projectDirectory"))
                .append(entityDetailsForm.getPackageName()).append(".").append(entityDetailsForm.getEntityName())
                .append(entityDetailsForm.isForceUpdateEntity()
                        ? AutobotConstants.EMPTY_SPACE_CHAR + environment.getProperty("entity.setup.force") : "")
                .append(AutobotConstants.NEW_LINE_CHAR);

        for (Field field : entityDetailsForm.getFields()) {
            if (!StringUtils.isEmpty(field.getFieldName()) && AutobotConstants.ENTITY.equals(entityDetailsForm.getEntityType())) {
                String extrafieldMetaText = "";
                String relationType = "";
                if (AutobotConstants.NUMBER.equals(field.getFieldType())) {
                    extrafieldMetaText = AutobotConstants.INTEGER_FORMAT1;
                } else if (AutobotConstants.DATE.equals(field.getFieldType())) {
                    extrafieldMetaText = AutobotConstants.DATE_FORMAT;
                }

                if (enumList.contains(field.getFieldType()) || daoClassNameList.contains(field.getFieldType())) {
                    if (daoClassNameList.contains(field.getFieldType())) {
                        if (AutobotConstants.SET.equals(field.getRelationType())) {
                            relationType = AutobotConstants.SET + AutobotConstants.EMPTY_SPACE_CHAR;
                        } else {
                            relationType = AutobotConstants.REFERENCE + AutobotConstants.EMPTY_SPACE_CHAR;
                        }
                    } else if (enumList.contains(field.getFieldType())) {
                        relationType = AutobotConstants.ENUM + AutobotConstants.EMPTY_SPACE_CHAR;
                    }

                    str.append(environment.getProperty("entity.setup.field") + AutobotConstants.EMPTY_SPACE_CHAR + relationType)
                            .append(creatorUtil.createAppendString(field.getFieldName() + extrafieldMetaText,
                                    false, false, "entity.setup.fieldName"))
                            .append(environment.getProperty("entity.Relations.type")).append(AutobotConstants.EMPTY_SPACE_CHAR).append(environment.getProperty("entity.setup.projectDirectory"))
                            .append(field.getFieldType()).append(AutobotConstants.NEW_LINE_CHAR);
                } else {
                    str.append(creatorUtil.createAppendString(field.getFieldType(),
                            false, false, "entity.setup.field"))
                            .append(creatorUtil.createAppendString(field.getFieldName() + extrafieldMetaText, false,
                                    "entity.setup.fieldName"));
                }


            } else if (!StringUtils.isEmpty(field.getFieldName()) && AutobotConstants.ENUM.equals(entityDetailsForm.getEntityType())) {
                str.append(creatorUtil.createAppendString(field.getFieldName(), false, "entity.setup.enum.constant"));
            }
        }
        str.append(AutobotConstants.NEW_LINE_CHAR);
        appendTORooFile(str.toString());
        mv.setViewName("redirect:entityDetails");
        model.put("projectName", autobot.getProjectDetails().getProjectName());
        logger.info(autobot.getProjectDetails().getProjectName() + " is added with Entity details[" + entityName + "]");
        return mv;
    }

    @RequestMapping(value = "/entityRelations", method = RequestMethod.GET)
    public ModelAndView viewEntityRelations(Map<String, Object> model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("entityDetailsForm", autobot.getEntityDetails());
        modelAndView.addObject("entityRelationsForm", autobot.getEntityRelationDetails());
        modelAndView.setViewName("entityRelations");
        model.put("projectName", autobot.getProjectDetails().getProjectName());
        model.put("daoClassNameList", daoClassNameList);
        model.put("enumList", enumList);
        return modelAndView;
    }

    @RequestMapping(value = "/entityRelations", method = RequestMethod.POST)
    public ModelAndView entityRelationsDetails(
            @Valid @ModelAttribute("entityRelationsForm") EntityRelationDetails entityRelationsDetailsForm,
            BindingResult result, Map<String, Object> model) {
        ModelAndView mv = new ModelAndView("entityRelations");
        if (result.hasErrors()) {
            mv.addObject("entityDetailsForm", autobot.getEntityDetails());
            mv.addObject("entityRelationsForm", autobot.getEntityRelationDetails());
            return mv;
        }

        StringBuffer str = new StringBuffer(environment.getProperty("entity.Relations.Details"));
        // Populate this upto focus text
        str.append(AutobotConstants.NEW_LINE_CHAR).append(AutobotConstants.NEW_LINE_CHAR).append(AutobotConstants.FORWARD_SLASH_CHAR)
                .append(entityRelationsDetailsForm.getEntityName()).append(AutobotConstants.EMPTY_SPACE_CHAR)
                .append(environment.getProperty("entity.Relations")).append(AutobotConstants.NEW_LINE_CHAR)
                .append(creatorUtil.createAppendString("", false, false, "entity.Relations.focus",
                        "entity.setup.class"))
                .append(creatorUtil.createAppendString(entityRelationsDetailsForm.getEntityName(), false,
                        "entity.setup.projectDirectory"));

        for (EntityRelationField field : entityRelationsDetailsForm.getFields()) {
            str.append(creatorUtil.createAppendString(field.getRelationType(), false, false, "entity.setup.field"))
                    .append(creatorUtil.createAppendString(field.getFieldName(), false, false,
                            "entity.setup.fieldName"))
                    .append(environment.getProperty("entity.Relations.type")).append(AutobotConstants.EMPTY_SPACE_CHAR)
                    .append(environment.getProperty("entity.setup.projectDirectory")).append(field.getFieldType())
                    .append(AutobotConstants.EMPTY_SPACE_CHAR);

            if (environment.getProperty("entity.Relations.refrence").equals(field.getRelationType())) {
                str.append(creatorUtil.createAppendString(field.getIsAggregation(), false, false,
                        "entity.Relations.field.aggregation"))
                        .append(creatorUtil.createAppendString(field.getJoinColumnName(), false,
                                "entity.Relations.joinColumnName"));
            } else {
                String[] appendValues = new String[]{field.getJoinTable(), field.getJoinColumns(),
                        field.getReferencedColumns(), field.getInverseJoinColumns(),
                        field.getInverseReferencedColumns()};

                str.append(creatorUtil.createFullStringValues(appendValues, "entity.Relations.joinTable",
                        "entity.Relations.joinColumns", "entity.Relations.referencedColumns",
                        "entity.Relations.inverseJoinColumns", "entity.Relations.inverseReferencedColumns"));
            }
        }
        appendTORooFile(str.toString());
        mv.setViewName("redirect:autoSetup");
        model.put("projectName", autobot.getProjectDetails().getProjectName());
        logger.info(autobot.getProjectDetails().getProjectName() + " is added with Entity Relations for "
                + entityRelationsDetailsForm.getEntityName());
        return mv;
    }

    @RequestMapping(value = "/autoSetup", method = RequestMethod.GET)
    public ModelAndView viewAutoSetup(Map<String, Object> model) {
        autobot.setAutoSetup(new AutoSetup());
        model.put("autoSetupForm", autobot.getAutoSetup());
        model.put("projectName", autobot.getProjectDetails().getProjectName());
        return new ModelAndView("autoSetup");
    }

    @RequestMapping(value = "/autoSetup", method = RequestMethod.POST)
    public ModelAndView autoSetup(@ModelAttribute("autoSetupForm") AutoSetup autoSetupForm, BindingResult result,
                                  Map<String, Object> model) {
        ModelAndView mv = new ModelAndView("configSuccess");
        autobot.setAutoSetup(autoSetupForm);
        StringBuffer str = new StringBuffer();
        // Append command for disabling .aj files creation
        str.append(creatorUtil.createAppendStringText(true, "autoSetup.disable.aj.files"));
        if (autoSetupForm.isAutogenerateJpaRepos()) {
            str.append(creatorUtil.createAppendStringText(true, "autoSetup.repositories",
                    "autoSetup.repositories.jpa.all"));
        }
        if (autoSetupForm.isAutogenerateServices()) {
            str.append(creatorUtil.createAppendStringText(false, "autoSetup.services", "autoSetup.services.all"));
        }

        if (autoSetupForm.isAutogenerateViews()) {
            str.append(creatorUtil.createAppendStringText(false, "autoSetup.weblayer", "autoSetup.weblayer.mvc.setup"))
                    .append(creatorUtil.createAppendString("autoSetup.weblayer.mvc.language",
                            autoSetupForm.getLanguage(), false))
                    .append(creatorUtil.createAppendStringText(false, "autoSetup.weblayer.mvc.controller",
                            "autoSetup.weblayer.mvc.view.setup", "autoSetup.weblayer.mvc.controller.all"))
                    .append(creatorUtil.createAppendString("autoSetup.json.all",
                            autoSetupForm.getAutogenerateJsonRemotingPackage(), false));
/*			for (String entityName : autoSetupForm.getEntityNames()) {
				str.append(environment.getProperty("autoSetup.weblayer.publishing")).append(NEW_LINE_CHAR)
						.append(environment.getProperty("autoSetup.weblayer.mvc.finder")).append(entityName)
						.append(NEW_LINE_CHAR).append(environment.getProperty("autoSetup.weblayer.mvc.finder"))
						.append(entityName).append(EMPTY_SPACE_CHAR)
						.append(environment.getProperty("autoSetup.weblayer.mvc.finder.responseType"))
						.append(NEW_LINE_CHAR);
			} */
        }

		/*if (autoSetupForm.isAutoGenerateJpaAudit()) {
			str.append(NEW_LINE_CHAR).append(environment.getProperty("autoSetup.jpa.audit.text")).append(NEW_LINE_CHAR)
					.append(environment.getProperty("autoSetup.jpa.audit.setup"));

			for (String entityAuditName : autoSetupForm.getEntityAuditNames()) {
				str.append(NEW_LINE_CHAR).append(environment.getProperty("autoSetup.jpa.audit.add"))
						.append(entityAuditName);
			}
		}*/

        appendTORooFile(str.toString());
        model.put("projectName", autobot.getProjectDetails().getProjectName());
        logger.info("Autosetup configuration for " + autobot.getProjectDetails().getProjectName() + "is Completed.");
        return mv;
    }

    @RequestMapping(value = "/runRoo", method = RequestMethod.GET)
    public ModelAndView runRoo(HttpServletRequest request)
            throws IOException, InterruptedException, URISyntaxException {
        String projectName = StringUtils.isEmpty(autobot.getProjectDetails().getProjectName()) ? AutobotConstants.DEFAULT_PROJECT
                : autobot.getProjectDetails().getProjectName();

        rooFile = new File("samples" + File.separator + projectName + ".roo");

        String rooFilePath = rooFile.getAbsolutePath();
        StringBuffer rooBatPathBuffer = new StringBuffer();

        String os = System.getProperty("os.name").toLowerCase();
        Runtime rt = Runtime.getRuntime();
        if (os.indexOf("win") >= 0) {
            rooBatPath = rooBatPath.toString().replaceFirst(AutobotConstants.FORWARD_SINGLE_SLASH_CHAR, "");
            rooBatPathBuffer.append(rooBatPath).append(environment.getProperty("spring.roo.version")
                    + AutobotConstants.FORWARD_SINGLE_SLASH_CHAR + "bin" + AutobotConstants.FORWARD_SINGLE_SLASH_CHAR + "roo.bat");
            String rooExecPath = "cmd /c start " + rooBatPathBuffer.toString() + " script --file " + rooFilePath;
            cleanUpGenCode();
            rt.exec(rooExecPath);
        } else if (os.indexOf("mac") >= 0) {
            File runBatFile = new File("runProject.sh");
            StringBuffer sb = new StringBuffer("cd ").append(rooBatPath)
                    .append(environment.getProperty("spring.roo.version") + File.separator + "bin" + File.separator)
                    .append(AutobotConstants.NEW_LINE_CHAR).append("chmod 777 *").append(AutobotConstants.NEW_LINE_CHAR).append("./roo.sh ")
                    .append(" script --file " + rooBatPath + "samples" + File.separator
                            + autobot.getProjectDetails().getProjectName() + ".roo");
            FileWriter fileWriter = new FileWriter(runBatFile, false);
            fileWriter.append(sb.toString());
            fileWriter.close();
            cleanUpGenCode();

            rt.exec("/usr/bin/open -a Terminal " + runBatFile.getAbsolutePath());
        }

        logger.info("Generating the " + autobot.getProjectDetails().getProjectName() + ".........");
        return new ModelAndView("redirect:success");
    }

    @RequestMapping(value = "/success", method = RequestMethod.GET)
    public ModelAndView success(Model model) {
        ModelAndView mv = new ModelAndView("success");
        mv.addObject("projectName", autobot.getProjectDetails().getProjectName());
        return mv;
    }

    @RequestMapping(value = "/copyProject", method = RequestMethod.GET)
    public ModelAndView copyProject(HttpServletRequest request)
            throws IOException, JAXBException, XmlPullParserException, InterruptedException {
        String projectCopyPath = System.getProperty("user.home") + File.separator + "Desktop" + File.separator
                + autobot.getProjectDetails().getProjectName();
        File projectFolder = new File(projectCopyPath + File.separator + "src");
        projectFolder.mkdirs();
        File pomFileCreatr = new File(projectCopyPath + File.separator + "pom.xml");
        pomFileCreatr.createNewFile();
        File srcFolderCreation = new File(projectCopyPath + File.separator + "src");
        File src = new File(rooBatPath + environment.getProperty("spring.roo.version") + AutobotConstants.FORWARD_SINGLE_SLASH_CHAR
                + "bin" + AutobotConstants.FORWARD_SINGLE_SLASH_CHAR + "src");
        File pomFile = new File(rooBatPath + environment.getProperty("spring.roo.version") + AutobotConstants.FORWARD_SINGLE_SLASH_CHAR
                + "bin" + AutobotConstants.FORWARD_SINGLE_SLASH_CHAR + "pom.xml");

        FileUtils.copyDirectory(src, srcFolderCreation);
        FileUtils.copyFile(pomFile, pomFileCreatr);
        FileUtils.deleteDirectory(src);
        FileUtils.forceDelete(pomFile);

        org.apache.maven.model.Model model = creatorUtil.pomCleanup(pomFileCreatr);
        String fileExt = "bat";
        String cmdStr = "cmd /c start";
        String callurl = "@START";
        String mvnCmd = "call ";
        if (System.getProperty("os.name").toLowerCase().indexOf("mac") >= 0) {
            fileExt = "sh";
            cmdStr = "/usr/bin/open -a Terminal";
            callurl = "open";
            mvnCmd = "";
        }
        File runBatFile = new File("run." + fileExt);
        FileWriter fileWriter = new FileWriter(runBatFile);
        String execJar = projectCopyPath + File.separator + "target" + File.separator + model.getArtifactId() + "-"
                + model.getVersion() + "-exec.jar";
        StringBuffer sb = new StringBuffer("cd " + projectCopyPath + AutobotConstants.NEW_LINE_CHAR);
        sb.append(mvnCmd + "mvn clean install -DskipTests").append(AutobotConstants.NEW_LINE_CHAR).append("java -jar " + execJar)
                .append(AutobotConstants.NEW_LINE_CHAR).append(callurl + " http://localhost:8080/");
        fileWriter.append(sb.toString());
        fileWriter.close();
        String rooExecPath = cmdStr + " run." + fileExt;
        Runtime.getRuntime().exec(rooExecPath);

        logger.info("Copy " + autobot.getProjectDetails().getProjectName() + " to Desktop is completed..");
        return new ModelAndView("redirect:/");
    }

    private void appendTORooFile(String commandStr) {
        try {
            String projectName = StringUtils.isEmpty(autobot.getProjectDetails().getProjectName()) ? AutobotConstants.DEFAULT_PROJECT
                    : autobot.getProjectDetails().getProjectName();
            rooFile = new File(rooBatPath + "samples" + File.separator + projectName + ".roo");
            FileWriter fileWriter = new FileWriter(rooFile, true);
            fileWriter.append(commandStr);
            fileWriter.close();
            sampleProjectList.add(projectName);
        } catch (Exception e) {
            logger.error(
                    "Failed to update " + autobot.getProjectDetails().getProjectName() + " configuration due to : ", e);
        }
    }

    private void cleanUpGenCode() throws IOException {
        File src = new File(rooBatPath + environment.getProperty("spring.roo.version") + AutobotConstants.FORWARD_SINGLE_SLASH_CHAR
                + "bin" + AutobotConstants.FORWARD_SINGLE_SLASH_CHAR + "src");
        File pomFile = new File(rooBatPath + environment.getProperty("spring.roo.version") + AutobotConstants.FORWARD_SINGLE_SLASH_CHAR
                + "bin" + AutobotConstants.FORWARD_SINGLE_SLASH_CHAR + "pom.xml");
        boolean needCleanUp = false;
        if (src.exists()) {
            FileUtils.deleteDirectory(src);
            needCleanUp = true;
        }

        if (pomFile.exists()) {
            FileUtils.forceDelete(pomFile);
            needCleanUp = true;
        }

        if (needCleanUp) {
            logger.info("Clean up of old generated code is Completed..");
        }
    }

}
