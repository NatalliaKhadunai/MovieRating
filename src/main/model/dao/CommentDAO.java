package main.model.dao;

import main.model.manager.QueryManager;
import main.model.entity.Comment;
import main.model.entity.Film;
import main.model.entity.TVSeries;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.print.Doc;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.sql.*;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Class {@code CommentDAO} is a class, with the help of which data about comments is extracted from database.
 * {@see main.model.entity.Comment}
 */

public class CommentDAO extends AbstractDAO {

    public CommentDAO() {
        super();
    }

    /**
     * Searches for all the comments made on given TVSeries
     * @param tvSeries entity, for which comments are sought.
     * @return list of found comments.
     */
    public List<Comment> getAllEntities(TVSeries tvSeries) {
        if (tvSeries == null) return null;
        List<Comment> commentList = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(QueryManager.getProperty("commentDAO.getAllEntities_TVSeries"));
            preparedStatement.setString(1, tvSeries.getName());
            ResultSet resultSet = preparedStatement.executeQuery();
            commentList = getListEntity(resultSet);
            preparedStatement.close();
        }
        catch (SQLException e) {
            logger.error(e);
        }
        finally {
            return commentList;
        }
    }

    /**
     * Searches for all the comments made on given TVSeries
     * @param film entity, for which comments are sought.
     * @return list of found comments.
     */
    public List<Comment> getAllEntities(Film film) {
        if (film == null) return null;
        List<Comment> commentList = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(QueryManager.getProperty("commentDAO.getAllEntities_Film"));
            preparedStatement.setString(1, film.getName());
            ResultSet resultSet = preparedStatement.executeQuery();
            commentList = getListEntity(resultSet);
            preparedStatement.close();
        }
        catch (SQLException e) {
            logger.error(e);
        }
        finally {
            return commentList;
        }
    }

    /**
     * Return List of comments data taken from given ResultSet.
     * @param resultSet value is a set of data to be disassemble.
     * @return list of comments.
     */
    private List<Comment> getListEntity(ResultSet resultSet) {
        List<Comment> commentList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Comment comment = new Comment();
                comment.setUserLogin(resultSet.getString("UserLogin"));
                comment.setContent(resultSet.getString("Content"));
                comment.setDate(resultSet.getDate("Date"));
                commentList.add(comment);
            }
        }
        catch (SQLException e) {
            logger.error(e);
        }
        return commentList;
    }

    /**
     * Add entity for film comment.
     * @param film represents film, on which comment was made.
     * @param login represents user, who made comment.
     * @param content represents content of the comment.
     * @param date represents date, when comment was made.
     */
    public void addEntity(Film film, String login, String content, Timestamp date) {
        if (film == null || login == null || login.equals("") ||
                content == null || content.equals("") || date == null) return;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QueryManager.getProperty("commentDAO.addEntity_Film"));
            preparedStatement.setInt(1, film.getID());
            preparedStatement.setString(2, login);
            preparedStatement.setString(3, content);
            preparedStatement.setTimestamp(4, date);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e) {
            logger.error(e);
        }
    }

    /**
     * Add entity for film comment.
     * @param tvSeries represents tvSeries, on which comment was made.
     * @param login represents user, who made comment.
     * @param content represents content of the comment.
     * @param date represents date, when comment was made.
     */
    public void addEntity(TVSeries tvSeries, String login, String content, Timestamp date) {
        if (tvSeries == null || login == null || login.equals("") ||
                content == null || content.equals("") || date == null) return;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QueryManager.getProperty("commentDAO.addEntity_TVSeries"));
            preparedStatement.setInt(1, tvSeries.getID());
            preparedStatement.setString(2, login);
            preparedStatement.setString(3, content);
            preparedStatement.setTimestamp(4, date);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e) {
            logger.error(e);
        }
    }

    /**
     * Search for comments, that were made after given timestamp.
     * @param film represents film, for which search is made.
     * @param date represents time border.
     * @return String value in XML-format, that contains info about found comments.
     */
    public String getLatestComments(Film film, Timestamp date, Locale locale) {
        if (film == null || date == null || locale == null) return null;
        String result = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QueryManager.getProperty("commentDAO.getLatestComments_Film"));
            preparedStatement.setString(1, date.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            result = commentsToXML(resultSet, locale);
            preparedStatement.close();
        }
        catch (SQLException e) {
            logger.error(e);
        }
        finally {
            return result;
        }
    }

    /**
     * Search for comments, that were made after given timestamp.
     * @param tvSeries represents TV Series, for which search is made.
     * @param date represents time border.
     * @return String value in XML-format, that contains info about found comments.
     */
    public String getLatestComments(TVSeries tvSeries, Timestamp date, Locale locale) {
        if (tvSeries == null || date == null || locale == null) return null;
        String result = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QueryManager.getProperty("commentDAO.getLatestComments_TVSeries"));
            preparedStatement.setString(1, date.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            result = commentsToXML(resultSet, locale);
            preparedStatement.close();
        }
        catch (SQLException e) {
            logger.error(e);
        }
        finally {
            return result;
        }
    }

    /**
     * Return string in xml-format, that represents set of comments.
     * @param resultSet set of data.
     * @return string in xml-format, that represents set of comments
     */
    private String commentsToXML(ResultSet resultSet, Locale locale) {
        DocumentBuilderFactory dFact = DocumentBuilderFactory.newInstance();
        DocumentBuilder build = null;
        try {
            build = dFact.newDocumentBuilder();
        }
        catch (ParserConfigurationException e) {
            logger.error(e);
        }
        Document doc = build.newDocument();
        Element root = createAndFillRootElement(doc, resultSet, locale);
        doc.appendChild(root);
        TransformerFactory tFact = TransformerFactory.newInstance();
        Transformer trans = null;
        try {
            trans = tFact.newTransformer();
        }
        catch (TransformerConfigurationException e) {
            logger.error(e);
        }
        StringWriter writer = new StringWriter();
        StreamResult streamResult = new StreamResult(writer);
        DOMSource source = new DOMSource(doc);
        try {
            trans.transform(source, streamResult);
        }
        catch (TransformerException e) {
            logger.error(e);
        }
        return writer.toString();
    }

    /**
     * Create root element for xml-document and fill it with data.
     * @param doc document, where elements are created.
     * @param resultSet set of data to fill-in xml-document.
     * @param locale value, represents the way some data should be formatted.
     * @return root element of the document.
     */
    private Element createAndFillRootElement(Document doc, ResultSet resultSet, Locale locale) {
        Element root = doc.createElement("comments");
        try {
            while (resultSet.next()) {
                Element member = doc.createElement("comment");
                root.appendChild(member);
                Element name = doc.createElement("userLogin");
                name.appendChild(doc.createTextNode(resultSet.getString("UserLogin")));
                member.appendChild(name);
                Element content = doc.createElement("content");
                content.appendChild(doc.createTextNode(resultSet.getString("Content")));
                member.appendChild(content);
                DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
                DateFormat tf = DateFormat.getTimeInstance(DateFormat.SHORT, locale);
                String formattedDate = df.format(resultSet.getTimestamp("Date"));
                String formattedTime = tf.format(resultSet.getTimestamp("Date"));
                Element dateTime = doc.createElement("date");
                dateTime.appendChild(doc.createTextNode(formattedDate + " " + formattedTime));
                member.appendChild(dateTime);
            }
        }
        catch (SQLException e) {
            logger.error(e);
        }
        return root;
    }
}
