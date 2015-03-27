package com.moviejukebox.thetvdb;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.moviejukebox.thetvdb.model.Actor;
import com.moviejukebox.thetvdb.model.Banners;
import com.moviejukebox.thetvdb.model.Episode;
import com.moviejukebox.thetvdb.model.Mirrors;
import com.moviejukebox.thetvdb.model.Series;
import com.moviejukebox.thetvdb.tools.LogFormatter;
import com.moviejukebox.thetvdb.tools.TvdbParser;
import com.moviejukebox.thetvdb.tools.WebBrowser;

/**
 * @author altman.matthew
 * @author stuart.boston
 */
public class TheTVDB {
    private static String apiKey = null;
    private static String xmlMirror = null;
    private static String bannerMirror = null;
    
    private static final String LOGGERNAME = "TheTVDB";
    private static Logger logger = null;
    private static LogFormatter logFormatter = new LogFormatter();
    private static ConsoleHandler logConsoleHandler = new ConsoleHandler();

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    public TheTVDB(String apiKey) {
        if (apiKey == null) {
            return;
        }
        
        setLogger(LOGGERNAME);
        setApiKey(apiKey);
        
        // Mirror information is called for when the get??Mirror calls are used
    }

    /**
     * Set the API Key
     * @param apiKey
     */
    public void setApiKey(String apiKey) {
        TheTVDB.apiKey = apiKey;
        logFormatter.addApiKey(apiKey);
    }

    /**
     * Return the logger information
     * @return
     */
    public static Logger getLogger() {
        return logger;
    }

    /**
     * Set the logger name
     * @param loggerName
     */
    public void setLogger(String loggerName) {
        // Check to see if we need to initalise the logger or not
        if (logger != null) {
            return;
        }
        
        logger = Logger.getLogger(loggerName);
        logConsoleHandler.setFormatter(logFormatter);
        logConsoleHandler.setLevel(Level.FINE);
        logger.addHandler(logConsoleHandler);
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.ALL);
    }

    /**
     * Get the mirror information from TheTVDb
     * @return True if everything is OK, false otherwise.
     */
    private static boolean getMirrors() {
        // If we don't need to get the mirrors, then just return
        if (xmlMirror != null && bannerMirror != null) {
            return true;
        }
        
        boolean mirrorsOK = true;
        
        Mirrors mirrors = new Mirrors(apiKey);
        xmlMirror = mirrors.getMirror(Mirrors.TYPE_XML);
        bannerMirror = mirrors.getMirror(Mirrors.TYPE_BANNER);
        
        if(xmlMirror == null) {
            logger.warning("TheTVDB: xmlMirror is null!");
            mirrorsOK = false;
        } else {
            xmlMirror += "/api/";
        }
        
        if(bannerMirror == null) {
            logger.warning("TheTVDB: bannerMirror is null!");
            mirrorsOK = false;
        } else {
            bannerMirror += "/banners/";
        }
        //zipMirror = mirrors.getMirror(Mirrors.TYPE_ZIP);

        if (!mirrorsOK) {
            logger.warning("There is a problem getting the mirror data from TheTVDB, this means it is likely to be down.");
        }
        
        return mirrorsOK;
    }
    
    /**
     * Set the web browser proxy information
     * @param host
     * @param port
     * @param username
     * @param password
     */
    public void setProxy(String host, String port, String username, String password) {
        WebBrowser.setProxyHost(host);
        WebBrowser.setProxyPort(port);
        WebBrowser.setProxyUsername(username);
        WebBrowser.setProxyPassword(password);
    }
    
    /**
     * Set the web browser timeout settings
     * @param webTimeoutConnect
     * @param webTimeoutRead
     */
    public void setTimeout(int webTimeoutConnect, int webTimeoutRead) {
        WebBrowser.setWebTimeoutConnect(webTimeoutConnect);
        WebBrowser.setWebTimeoutRead(webTimeoutRead);
    }
    
    /**
     * Get the series information
     * @param id
     * @param language
     * @return
     */
    public Series getSeries(String id, String language) {
        String urlString = getXmlMirror() + apiKey + "/series/" + id + "/" + (language!=null?language+".xml":"");
        
        List<Series> seriesList = TvdbParser.getSeriesList(urlString);
        if (seriesList.isEmpty()) {
            return null;
        } else {
            return seriesList.get(0);
        }
    }
    
    /**
     * Get a specific episode's information
     * @param seriesId
     * @param seasonNbr
     * @param episodeNbr
     * @param language
     * @return
     */
    public Episode getEpisode(String seriesId, int seasonNbr, int episodeNbr, String language) {
        String urlString = getXmlMirror() + apiKey + "/series/" + seriesId + "/default/" + seasonNbr + "/" + episodeNbr + "/" + (language!=null?language+".xml":"");
        return TvdbParser.getEpisode(urlString);
    }

    /**
     * Get a specific DVD episode's information
     * @param seriesId
     * @param seasonNbr
     * @param episodeNbr
     * @param language
     * @return
     */
    public Episode getDVDEpisode(String seriesId, int seasonNbr, int episodeNbr, String language) {
        String urlString = getXmlMirror() + apiKey + "/series/" + seriesId + "/dvd/" + seasonNbr + "/" + episodeNbr + "/" + (language!=null?language+".xml":"");
        return TvdbParser.getEpisode(urlString);
    }

    /**
     * Get a list of banners for the series id 
     * @param id
     * @return
     */
    public String getSeasonYear(String id, int seasonNbr, String language) {
        String year = null;

        Episode episode = getEpisode(id, seasonNbr, 1, language);
        if (episode != null) {
            if (episode.getFirstAired() != null && !episode.getFirstAired().isEmpty()) {
                try {
                    Date date = dateFormat.parse(episode.getFirstAired());
                    if (date != null) {
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(date);
                        year = "" + cal.get(Calendar.YEAR);
                    }
                } catch (Exception ignore) {}
            }
        }

        return year;
    }

    public Banners getBanners(String seriesId) {
        String urlString = getXmlMirror() + apiKey + "/series/" + seriesId + "/banners.xml";
        return TvdbParser.getBanners(urlString);
    }
    
    
    /**
     * Get a list of actors from the series id
     * @param SeriesId
     * @return
     */
    public List<Actor> getActors(String seriesId) {
        String urlString = getXmlMirror() + apiKey + "/series/" + seriesId + "/actors.xml";
        return TvdbParser.getActors(urlString);
    }
    

    public List<Series> searchSeries(String title, String language) {
        String urlString = getXmlMirror() + "GetSeries.php?seriesname=";
        try {
            urlString += URLEncoder.encode(title, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // Try and use the raw title
            urlString += title;
        }
        urlString += (language!=null?"&language="+language:"");
        return TvdbParser.getSeriesList(urlString);
    }
    
    /**
     * Get the XML Mirror URL
     * @return
     */
    public static String getXmlMirror() {
        // Force a load of the mirror information if it doesn't exist
        getMirrors();
        return xmlMirror;
    }
    
    /**
     * Get the Banner Mirror URL
     * @return
     */
    public static String getBannerMirror() {
        // Force a load of the mirror information if it doesn't exist
        getMirrors();
        return bannerMirror;
    }
    
}
