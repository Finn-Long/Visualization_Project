package com.visualization.logtransformer.transformers;

import com.google.cloud.Timestamp;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.visualization.logserver.entity.Content;
import com.visualization.logserver.entity.Milestone;
import com.visualization.logserver.entity.Student;
import org.springframework.context.ApplicationContext;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;

public class CyverseLogTransformer extends Transformer{
    private static Object lock;

    private final Map<String, String> milestoneIdentifier = new HashMap<>();

    private final String source = "Cyverse";

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z (z)");

    public CyverseLogTransformer(String jsonFileName, ApplicationContext context) {
        super(jsonFileName, context);
        this.lock = new Object();
        populateMilestoneIdentifier();
    }

    public void setLog() throws IOException, ExecutionException, InterruptedException, ParseException {
        JsonObject rawLog = getLogJsonHelper();
        JsonArray logArray = rawLog.get("logs").getAsJsonArray();
        String[] randomNames = getRandomNamesHelper(logArray.size());
        for (int i = 0; i < logArray.size(); i ++) {
            JsonObject log = logArray.get(i).getAsJsonObject();
            String studentId = log.get("log_id").getAsString();
            String fakeName = randomNames[i];
            synchronized (lock) {
                if (getNamePairHelper(studentId) == null) {
                    setNamePairHelper(studentId, fakeName);
                }else {
                    fakeName = getNamePairHelper(studentId);
                }
            }
            JsonArray allLogs = log.get("log").getAsJsonObject().get("logArray").getAsJsonArray();
            for (int j = 0; j < allLogs.size(); j ++) {
                JsonObject individualLog = allLogs.get(j).getAsJsonObject();
                String behavior = validateGetHelper(individualLog, "event");
                //String eventType = validateGetHelper(individualLog, "eventType");
                String result = validateGetHelper(individualLog, "url");
                Student student = new Student(studentId, fakeName);
                Content content = new Content(behavior,result);
                Milestone milestone;
                String desc = containsHelper(result);
                if (desc == null) {
                    milestone = new Milestone(false);
                }else {
                    milestone = new Milestone(desc, true);
                }
                String timestampString = validateGetHelper(individualLog, "timestamp");
                Timestamp timestamp = getCyverseTimestampHelper(timestampString);
                try{
                    addLogHelper(student,milestone,content,source,timestamp);
                }catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
        System.out.println("Successful save all logs to database");
    }

    public Timestamp getCyverseTimestampHelper(String timestampString) throws ParseException {
        String timeZoneString = timestampString.substring(timestampString.lastIndexOf("GMT") + 3, timestampString.lastIndexOf(")"));
        TimeZone timeZone = TimeZone.getTimeZone("GMT" + timeZoneString);
        simpleDateFormat.setTimeZone(timeZone);
        Date date = simpleDateFormat.parse(timestampString);
        Timestamp timestamp = Timestamp.ofTimeSecondsAndNanos(date.getTime() / 1000,
                (int) ((date.getTime() % 1000) * 1000000));
        return timestamp;
    }

    public String validateGetHelper(JsonObject obj, String key) {
        if (obj.get(key) != null) {
            return obj.get(key).getAsString();
        }else {
            return "N/A";
        }
    }

    public JsonObject getLogJsonHelper() {
        File file = new File("./" + getJsonFileName());
        try (FileReader reader = new FileReader(file)) {
            JsonObject obj = JsonParser.parseReader(reader).getAsJsonObject();
            return obj;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String containsHelper(String url) {
        for (Map.Entry<String, String> set :
                milestoneIdentifier.entrySet()) {
            String key = set.getKey();
            String value = set.getValue();
            if (url.contains(key)) {
                return value;
            }
        }
        return null;
    }

    private void populateMilestoneIdentifier() {
        String[] quiz_urls = {
                "1FAIpQLSddCGvt7FriT0z3PhiLvi43-Vi9yBfla1Yi6ABQeJK68zdmsw/formResponse",
                "1FAIpQLScrLQ6sPbKsJE_XWPW9KoPm_mzcXixBORRJBDNztE6RLanq-Q/formResponse",
                "1FAIpQLScUzQHQOFezUmID7lwJDgGhxgjtnjHBFNfMjCU_7aWoEFXU6A/formResponse",
                "1FAIpQLSdio-36gufdOH-iwlcAroKij-wnIAPL0-MQgRi6CDA12Fm3Cg/formResponse",
                "1FAIpQLSeHeBZ_LH7kzg9yW_DzKur4yqWF9gdO0LWm3KCCU2u6WypzEQ/formResponse",
                "1FAIpQLSfXUX5Jr2moJDv7O7eQYjRjoyrk5NsbHFXEXYFfWoTMcXF99A/formResponse",
                "1FAIpQLScQaX9ukw_eyaA7aMD6Pl_4q_KJ1r-ARxPN5uXM_bsvB95n1w/formResponse",
                "1FAIpQLSc8qQblV-rqShUmf8zIaZDNoJlid2lG2VrlM0oH-Kz5Sb3klg/formResponse",
                "1FAIpQLSfy8nP9pGmEgliCFJG1PjwySgoHKsQNP_IZB0C_hksLi0hOiw/formResponse",
                "1FAIpQLSe4qhVC3vFo6MDiDFUs7xFELH1VEyV4mqdlN-m50DozlkESRw/formResponse",
                "1FAIpQLSdgm5NKev7ZiSL_7DswMQdpTJEzL45LVc6dXt5fUuCYo90NGA/formResponse",
                "1FAIpQLSeg72q8HBIiJCBS54pKvlfPXbad7JwxdNKQC5KrC4OWoOka3A/formResponse",

                "1FAIpQLScmGaKjinmJ7qUHWCmWw0mxN5g-JazjXQD-7M3GZ9EC5HGI2g/formResponse",
                "1FAIpQLSei9Pkns752nj36ayhoM-plQeJY1UlIMHn6phC_scoy_9-4CA/formResponse",
                "1FAIpQLSdP3_5w_CW0K141FLwsF0b_uowA1Rt1aHMLxS4okg03VsahLg/formResponse",
                "1FAIpQLScWg4k3vzAWe_t9H_IcqoTRBHw-iEDcU91TnyxJbKuMgRjByw/formResponse",
                "1FAIpQLSfD16aU73_yIXLpcVl9CcSIBCwzzT5lXIdj_ZQcIw7iAow-kQ/formResponse",
                "1FAIpQLSeIcAbANhDPvZBw5AzTDx9nyFcD76Tv9T1ixs9iw71t2YnmhQ/formResponse",
                "1FAIpQLScQWaqoa0WPVMjpI2RlgihmInju6NK5UWAzaOs7cWMSmBxLGg/formResponse",
                "1FAIpQLSdsHvNiyyOK6-gDU4anJW_Tpx7_tCZF9SrrvHnz227adSNwhA/formResponse",
                "1FAIpQLScku4KLqUCLAD6rkFapwK4Ywvm3URDnToj3Oa0KqTueIK_8Vw/formResponse",
                "1FAIpQLSeqZOjy2fESBbWOtq-r3E38d3Lr9uV17xt-TZIO3WL8FuO53A/formResponse",
                "1FAIpQLSdVPiy7-pDvKVLjvrKZwQBrm53jdufPce-lCA9tUxarf_ofvg/formResponse",
                "1FAIpQLSeojtdyjb1OUwaRj23FLdmgSAEBABSyMyKxORzuTRxgGLk-IQ/formResponse",
                "1FAIpQLSf2reh4lCauC_8SjF1szyWS2RQQMq4D9BYaFOJiKB2BIFmtGg/formResponse",
                "1FAIpQLSc4b0485oI5Xy8fowLheMiI3aNOuwDZGDLtHvD2q3cgzBCnHw/formResponse",

                "1FAIpQLSe1AbMbUslRViSCPOILByQcZ6g7iDcI_TxTyS0Uia_t1F4tiQ/formResponse",
                "1FAIpQLSc27KcDFFKijSuqgWbWVfdloK-zxQYUjvhR7qlXwhlyALXCIw/formResponse",
                "1FAIpQLSe8XNS0aMWvM5PYep4UUtkx2B5ufoxVsm1tRALbYOv3UjLriQ/formResponse",
                "1FAIpQLSf4Q6rKo_mNIhabwNLzSy-GIlHeSl3aU0S4vLCzb_FvrXtn1g/formResponse",
                "1FAIpQLSf2tv8MV-HPRcjrOVsHjas04akdQnpKlk5NB7OANTU04PUxVA/formResponse",
                "1FAIpQLSfy_etgaf5f4O7ARir0HvTTx_Y1JrgwF-U2D_ntxbqk3gfBbA/formResponse",
                "1FAIpQLSfz3Qh3CNc8zBsImuqIcMXSpYJg8Gh_Oai_n8PHO-H7MRtwjg/fomrResponse",
                "1FAIpQLSeg5XYiu1zS9v-J4N3QozcPkoWXflUnCjTf0Y3QoyRLh2qWJQ/formResponse",
                "1FAIpQLScazx0aKRqUKqnV2r0vLrN3WEoT8rCxcxOOY8rl3VuwDTe-Sw/formResponse",
                "1FAIpQLSdyrbSwWJM-JfF8FFwrnhxJROJds63yw78Lb-eN9zZYnJpDLA/formResponse",
                "1FAIpQLSeQ1yD1F2RU2r420eqTTTbUXKqdzmbifb_jhD3yiZ5GZy9jXw/formResponse",
                "1FAIpQLSeExwoAFyO2tIcH3NfhnP9jR8Syq1Sy4YOoGcpp5y2dDE0Snw/formResponse",
                "1FAIpQLSetGb7kVNbBpMYv-ge14aNiP2K0HiH36Ql1ZXFLZ9lGQHWuUA/formResponse",
                "1FAIpQLScQz3qCnuue3wx9bI4alb5egIjH-zPXyxhBlRkTdEZEckCcaw/formResponse",
                "1FAIpQLSfH-RbJYf-x9FDYeKsw2nUKs-wzHHpwZmE8OIBIc6BjKjGbaA/formResponse",
                "1FAIpQLScBtBrhXMnqXbob1vZDSOFHGbTVZWRs6mcChM_P9-i2Mk7SQg/formResponse",
                "1FAIpQLScYMIDV2Mbo26asDQpcFPDHaaihRY9IJU5-BjXygc-KkzPY9Q/formResponse",
                "1FAIpQLSd9-TB1m7ZyZzZHPNRZ7S41sbV4BHxwBcYJlePogWDsRyeDlA/formResponse",
                "1FAIpQLSc9AZubmmAxBP1qWT7AIARqpdvrZ1i4edHFK4NPl36Qm5WXEg/formResponse",
                "1FAIpQLSdG_lnZfZiL7v2mPIkAzp8779AqwyPz5CHY7I2kt1ODshX6Qg/formResponse",
                "1FAIpQLSczs6f10KzQGX00wiYSmP7hSpLQfvrT7SP6oVnlUGTGunZeMA/formResponse",
                "1FAIpQLSfFXH9FdEXfIWu8JOQdSJ8pm0jK0GZMlDFJueeY3aII0C9H_Q/formResponse",
                "1FAIpQLSfBlK2slNhKOFTLobeWlug44_bzFFo_vJPt2QNWLj0dALOfXw/formResponse",
                "1FAIpQLSddYE-x27SFOGufUR408nmRuigmzE_GEpk4hW3PRIPexD8F1w/formResponse",
                "1FAIpQLSf06kuzfcj5oLz9M_rLQDYBOPLwMGQd7csmW_7OX2kipzTcog/formResponse",
                "1FAIpQLSf-LxagwwhvMv2Ort0G7t2oI0KejCbzVG350tvEatu6wrso-g/formResponse",
                "1FAIpQLSePOQOf9PDDciiIW0BrO_qNCEDQMxGsRoPj1SW41BTrmUjtzQ/formResponse",
                "1FAIpQLSeInEVkhm24ZAnsgsySiP-8nkrZHR6i6ByCcLdm5jlj9tJb7Q/formResponse",
                "1FAIpQLSfV-K96T4a17Rds8NpMD6279raYeR6yHAiK0CzZEmrkD4K_Ig/formResponse",
                "1FAIpQLSdPCfA8lvIllZVOKkAVBLlvqAxNCT0PctriYhGTQiXdH0fzjQ/formResponse",
                "1FAIpQLScSEZDJSnVDEsIymk29DQ7EU4XFTRrUQJ7Lezgfr3aXdDlKbA/formResponse",
                "1FAIpQLSfLeFWj4lcIB0iR74IC15B6o5Khu6QKRY1Evw2TwH-hji9Cmw/formResponse"
        };

        String[] quiz_names = {
                "DNA Subway in progress: Pre-Quiz: File System1",
                "DNA Subway in progress: Post-Quiz: File System1",
                "DNA Subway in progress: Pre-Quiz: Basic Computation",
                "DNA Subway in progress: Post-Quiz: Basic Computation",
                "DNA Subway in progress: Pre-Quiz: Basic Genetics",
                "DNA Subway in progress: Post-Quiz: Basic Genetics",
                "DNA Subway in progress: Pre-Quiz: FastX Toolkit",
                "DNA Subway in progress: Post-Quiz: FastX Toolkit",
                "DNA Subway in progress: Pre-Quiz: Kallisto + Sleuth",
                "DNA Subway in progress: Post-Quiz: Kallisto + Sleuth",
                "DNA Subway in progress: Pre-Quiz: Cloud Computing",
                "DNA Subway Completed: Post-Quiz: Cloud Computing",

                "DNA Discovery in progress: Pre-Quiz: File System2",
                "DNA Discovery in progress: Post-Quiz: File System2",
                "DNA Discovery in progress: Pre-Quiz: Composite | Pipe",
                "DNA Discovery in progress: Post-Quiz: Composite | Pipe",
                "DNA Discovery in progress: Pre-Quiz: Access List",
                "DNA Discovery in progress: Post-Quiz: Access List",
                "DNA Discovery in progress: Pre-Quiz: Access Inheritance",
                "DNA Discovery in progress: Post-Quiz: Access Inheritance",
                "DNA Discovery in progress: Pre-Quiz: Access Overriding",
                "DNA Discovery in progress: Post-Quiz: Access Overriding",
                "DNA Discovery in progress: Pre-Quiz: Permission Override",
                "DNA Discovery in progress: Post-Quiz: Permission Override",
                "DNA Discovery in progress: Pre-Quiz: Tree Accesses",
                "DNA Discovery Completed: Post-Quiz: Tree Accesses",

                "DNA Command-Line in progress: Pre-Quiz: Bash-Scope",
                "DNA Command-Line in progress: Post-Quiz: Bash-Scope",
                "DNA Command-Line in progress: Pre-Quiz: Directories & Files",
                "DNA Command-Line in progress: Post-Quiz: Directories & Files",
                "DNA Command-Line in progress: Pre-Quiz: Long Listing",
                "DNA Command-Line in progress: Post-Quiz: Long Listing",
                "DNA Command-Line in progress: Pre-Quiz: Command Anatomy",
                "DNA Command-Line in progress: Post-Quiz: Command Anatomy",
                "DNA Command-Line in progress: Pre-Quiz: CD",
                "DNA Command-Line in progress: Post-Quiz: CD",
                "DNA Command-Line in progress: Pre-Quiz: Built-In vs External",
                "DNA Command-Line in progress: Post-Quiz: Built-In vs External",
                "DNA Command-Line in progress: Pre-Quiz: Relative Names",
                "DNA Command-Line in progress: Post-Quiz: Relative Names",
                "DNA Command-Line in progress: Pre-Quiz: CAT & Standard IO",
                "DNA Command-Line in progress: Post-Quiz: CAT & Standard IO",
                "DNA Command-Line in progress: Pre-Quiz: I/O Redirect",
                "DNA Command-Line in progress: Post-Quiz: I/O Redirect",
                "DNA Command-Line in progress: Pre-Quiz: '>>' and '*'",
                "DNA Command-Line in progress: Post-Quiz: '>>' and '*'",
                "DNA Command-Line in progress: Pre-Quiz: Manipulate Nodes",
                "DNA Command-Line in progress: Post-Quiz: Manipulate Nodes",
                "DNA Command-Line in progress: Pre-Quiz: PATH & Variables",
                "DNA Command-Line in progress: Post-Quiz: PATH & Variables",
                "DNA Command-Line in progress: Pre-Quiz: Pipes",
                "DNA Command-Line in progress: Post-Quiz: Pipes",
                "DNA Command-Line in progress: Pre-Quiz: Files & Permissions",
                "DNA Command-Line in progress: Post-Quiz: Files & Permissions",
                "DNA Command-Line in progress: Pre-Quiz: Looping",
                "DNA Command-Line in progress: Post-Quiz: Looping",
                "DNA Command-Line in progress: Pre-Quiz: Unix Permissions",
                "DNA Command-Line in progress: Post-Quiz: Unix Permissions"
        };
        for (int i = 0; i < quiz_urls.length; i ++) {
            milestoneIdentifier.put(quiz_urls[i], quiz_names[i]);
        }
    }
}
