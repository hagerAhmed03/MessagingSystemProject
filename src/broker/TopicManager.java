package broker;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.HashMap;
import org.json.simple.JSONObject;

public class TopicManager {

    Map<String, Integer> allTopics;
    DatabaseConnectionTwo connectionManagement;

    /*
     * hager
     */

    public TopicManager() {
        allTopics = new HashMap<String, Integer>();
        connectionManagement = new DatabaseConnectionTwo();
        connectionManagement.setDataSource();
        loadTopics();

    }

    public void loadTopics() {
        try {

            Connection con = connectionManagement.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from topic");

            while (rs.next())
                allTopics.put(rs.getString(1), rs.getInt(2));

            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            System.out.println("load exception" + e);
        }

    }

    public JSONObject getMessage(String topicName, int index) {
        JSONObject obj = new JSONObject();

        if (checktopicExist(topicName)) {
            Integer maxIndex = allTopics.get(topicName);
            String msg = null;

            if (index > maxIndex) {
                obj.put("status", false);
                obj.put("message", "OUT INDEX");
                return obj;
            }

            try {
                index--;
                String query = "select content from message where topicName=? ORDER BY messageIndex LIMIT " + index
                        + ",1";

                Connection con = connectionManagement.getConnection();
                PreparedStatement stmt = con.prepareStatement(query);
                stmt.setString(1, topicName);

                ResultSet rs = stmt.executeQuery();
                if (rs.next())
                    msg = rs.getString(1);

                rs.close();
                stmt.close();
                con.close();

                obj.put("status", true);
                obj.put("message", msg);
                return obj;

            } catch (Exception e) {

                obj.put("status", false);
                obj.put("message", "ERROR");
                return obj;
            }

        }

        obj.put("status", false);
        obj.put("message", "TOPIC NOT EXIST");
        return obj;

    }

    public boolean checktopicExist(String topicName) {
        Integer value = allTopics.get(topicName);
        if (value == null)
            return false;

        return true;
    }

    /*
     * azza
     */
    public boolean addMessage(String topicName, String message) {
        boolean result=false;
        if (checktopicExist(topicName)) {

            int index = allTopics.get(topicName);

            try {

                try (Connection conn=connectionManagement.getConnection()) {
                    conn.setAutoCommit(false);
                    String query = " insert into message (topicName,content)" + " values (?,?)";
                    String queryUpdate = " update topic set size=? where name=?";

                    try (PreparedStatement preparedStmtInsert = conn.prepareStatement(query)) {
                        //preparedStmtInsert.setInt(1, index);
                        preparedStmtInsert.setString(1, topicName);
                        preparedStmtInsert.setString(2, message);

                        preparedStmtInsert.execute();

                    }

                    try (PreparedStatement preparedStmtUpdate = conn.prepareStatement(queryUpdate)) {
                        preparedStmtUpdate.setInt(1, index + 1);
                        preparedStmtUpdate.setString(2, topicName);
                        preparedStmtUpdate.execute();
                        allTopics.put(topicName, index + 1);
                        result = true;

                    }

                    conn.commit();
                }

            } catch (Exception e) {
                System.out.println(e + "Sorry Failed to add message");
            }

        } else {

            addNewTopic(topicName);
            addMessage(topicName, message);
            result =true;

        }
        return result;
    }

    public void addNewTopic(String topicName) {
        try {
            Connection connAddNew=connectionManagement.getConnection();
            String query = "insert into topic (name,size)" + " values (?,?)";
            PreparedStatement preparedStmt = connAddNew.prepareStatement(query);
            preparedStmt.setString(1, topicName);
            preparedStmt.setInt(2, 0);
            preparedStmt.execute();
            allTopics.put(topicName, 0);
            preparedStmt.close();
            connAddNew.close();
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
