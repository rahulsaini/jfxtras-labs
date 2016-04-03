package jfxtras.labs.icalendar.properties.component.time.start;

import java.time.temporal.Temporal;

import jfxtras.labs.icalendar.properties.TimeZoneProperty;

public abstract class DateTimeStart<T, U extends Temporal> extends TimeZoneProperty<T, U>
{
    public DateTimeStart(U temporal)
    {
        super();
        setValue(temporal);
    }

    public DateTimeStart(String propertyString)
    {
        super(propertyString);
    }
    
    public DateTimeStart(DateTimeStart<T,U> source)
    {
        super(source);
    }
    
    public DateTimeStart()
    {
        super();
    }    
}