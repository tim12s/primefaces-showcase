/*
 * Copyright 2009-2016 PrimeTek.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.primefaces.showcase.view.data.timeline;

import org.primefaces.event.timeline.TimelineSelectEvent;
import org.primefaces.model.timeline.TimelineEvent;
import org.primefaces.model.timeline.TimelineModel;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDate;

@Named("basicTimelineView")
@ViewScoped
public class BasicTimelineView implements Serializable {  
  
    private TimelineModel model;  
  
    private boolean selectable = true;  
    private boolean zoomable = true;  
    private boolean moveable = true;  
    private boolean stackEvents = true;  
    private String eventStyle = "box";  
    private boolean axisOnTop;  
    private boolean showCurrentTime = true;  
    private boolean showNavigation = false;  
  
    @PostConstruct  
    protected void initialize() {  
        model = new TimelineModel();  
  
        model.add(new TimelineEvent("PrimeUI 1.1", LocalDate.of(2014, 6, 12)));
        model.add(new TimelineEvent("PrimeFaces 5.1.3", LocalDate.of(2014, 10, 11)));
        model.add(new TimelineEvent("PrimeUI 2.2", LocalDate.of(2015, 12, 8)));
        model.add(new TimelineEvent("Sentinel-Layout 1.1", LocalDate.of(2015, 3, 10)));
        model.add(new TimelineEvent("Spark-Layout 1.0", LocalDate.of(2015, 4, 3)));
        model.add(new TimelineEvent("Ronin-Layout 1.0", LocalDate.of(2015, 5, 15)));
        model.add(new TimelineEvent("Modena-Layout 1.0", LocalDate.of(2015, 7, 10)));
        model.add(new TimelineEvent("Rio-Layout 1.0", LocalDate.of(2015, 6, 15)));
        model.add(new TimelineEvent("Adamantium-Layout 1.0", LocalDate.of(2015, 9, 4)));
        model.add(new TimelineEvent("Titan-Layout 1.0", LocalDate.of(2015, 12, 14)));
        model.add(new TimelineEvent("Volt-Layout 1.0", LocalDate.of(2015, 10, 12)));
        model.add(new TimelineEvent("Atlas-Layout 1.0", LocalDate.of(2016, 1, 28)));
        model.add(new TimelineEvent("PrimeUI 4.1.0", LocalDate.of(2016, 2, 24)));
        model.add(new TimelineEvent("PrimeFaces 5.3.8", LocalDate.of(2016, 2, 29)));
        model.add(new TimelineEvent("PrimeNG 0.5", LocalDate.of(2016, 2, 29)));
    }  
  
    public void onSelect(TimelineSelectEvent e) {  
        TimelineEvent timelineEvent = e.getTimelineEvent();  
  
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected event:", timelineEvent.getData().toString());  
        FacesContext.getCurrentInstance().addMessage(null, msg);  
    }  
  
    public TimelineModel getModel() {  
        return model;  
    }  
  
    public boolean isSelectable() {  
        return selectable;  
    }  
  
    public void setSelectable(boolean selectable) {  
        this.selectable = selectable;  
    }  
  
    public boolean isZoomable() {  
        return zoomable;  
    }  
  
    public void setZoomable(boolean zoomable) {  
        this.zoomable = zoomable;  
    }  
  
    public boolean isMoveable() {  
        return moveable;  
    }  
  
    public void setMoveable(boolean moveable) {  
        this.moveable = moveable;  
    }  
  
    public boolean isStackEvents() {  
        return stackEvents;  
    }  
  
    public void setStackEvents(boolean stackEvents) {  
        this.stackEvents = stackEvents;  
    }  
  
    public String getEventStyle() {  
        return eventStyle;  
    }  
  
    public void setEventStyle(String eventStyle) {  
        this.eventStyle = eventStyle;  
    }  
  
    public boolean isAxisOnTop() {  
        return axisOnTop;  
    }  
  
    public void setAxisOnTop(boolean axisOnTop) {  
        this.axisOnTop = axisOnTop;  
    }  
  
    public boolean isShowCurrentTime() {  
        return showCurrentTime;  
    }  
  
    public void setShowCurrentTime(boolean showCurrentTime) {  
        this.showCurrentTime = showCurrentTime;  
    }  
  
    public boolean isShowNavigation() {  
        return showNavigation;  
    }  
  
    public void setShowNavigation(boolean showNavigation) {  
        this.showNavigation = showNavigation;  
    }  
}  
