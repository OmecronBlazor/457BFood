package algorithm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

public class EventTable {

    private LinkedHashMap< String, EventElement > event_table;

    public EventTable( Preference preference )
    {
        event_table = new LinkedHashMap< String, EventElement >();

        event_table.put( EventType.DINNER.event_name(), new EventElement( EventType.DINNER, preference, 0 ) );
        event_table.put( EventType.BREAKFAST.event_name(), new EventElement( EventType.BREAKFAST, preference, 1 ) );
        event_table.put( EventType.LUNCH.event_name(), new EventElement( EventType.LUNCH, preference, 2 ) );
        event_table.put( EventType.SNACK.event_name(), new EventElement( EventType.SNACK, preference, 3 ) );
        event_table.put( EventType.DIET.event_name(), new EventElement( EventType.DIET, preference, 4 ) );
        event_table.put( EventType.WEEKEND.event_name(), new EventElement( EventType.WEEKEND, preference, 5 ) );
        event_table.put( EventType.STUDYING.event_name(), new EventElement( EventType.STUDYING, preference, 6 ) );
        event_table.put( EventType.BIG_DAY.event_name(), new EventElement( EventType.BIG_DAY, preference, 7 ) );
        event_table.put( EventType.EXOTIC.event_name(), new EventElement( EventType.EXOTIC, preference, 8 ) );
    }

    public EventTable( List<EventElement> eventList)
    {
        event_table = new LinkedHashMap< String, EventElement >();

        for( EventElement event: eventList ) {
            event_table.put( event.event_name(), event );
        }
    }

    public void addEvent(EventElement eventElement)
    {
        event_table.put( eventElement.event_name(), eventElement );
    }

    public EventElement getEvent ( String name )
    {
        for( String event_name : event_table.keySet() )
        {
            if( event_name.equals(name) )
                return event_table.get( name );
        }

        return null;
    }

    public Collection<EventElement> getAllEvents()
    {
        return event_table.values();
    }

    public EventElement removeEvent(EventElement eventElement)
    {
        return event_table.remove(eventElement.event_name());
    }
}
