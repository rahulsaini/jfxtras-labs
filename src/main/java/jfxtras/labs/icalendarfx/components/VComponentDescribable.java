package jfxtras.labs.icalendarfx.components;

import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import jfxtras.labs.icalendarfx.properties.component.descriptive.Attachment;
import jfxtras.labs.icalendarfx.properties.component.descriptive.Summary;

/**
 * Describable VComponents
 * 
 * @author David Bal
 * @see VEvent
 * @see VTodoOld
 * @see VJournal
 * @see VAlarmOld
 *  */
public interface VComponentDescribable extends VComponentNew
{
    /**
     * ATTACH: Attachment
     * RFC 5545 iCalendar 3.8.1.1. page 80
     * This property provides the capability to associate a
     * document object with a calendar component.
     * 
     * Example:
     * ATTACH;FMTTYPE=application/postscript:ftp://example.com/pub/
     *  reports/r-960812.p
     */
    ObservableList<Attachment<?>> getAttachments();
    void setAttachments(ObservableList<Attachment<?>> properties);
    
    /**
     * SUMMARY:
     * RFC 5545 iCalendar 3.8.1.12. page 83
     * This property defines a short summary or subject for the calendar component 
     * Example:
     * SUMMARY:Department Party
     * */
    Summary getSummary();
    ObjectProperty<Summary> summaryProperty();
    void setSummary(Summary summary);
}