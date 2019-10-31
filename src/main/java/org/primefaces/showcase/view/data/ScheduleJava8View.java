/*
 * Copyright 2009-2014 PrimeTek.
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
package org.primefaces.showcase.view.data;

import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.*;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDateTime;

@Named
@ViewScoped
public class ScheduleJava8View implements Serializable {

	private ScheduleModel eventModel;
	
	private ScheduleModel lazyEventModel;

	private ScheduleEvent event = new DefaultScheduleEvent();

	private boolean showWeekends = true;
	private boolean tooltip = true;
	private boolean allDaySlot = true;

	private String timeFormat;
	private String slotDuration="00:30:00";
	private String slotLabelInterval;
	private String scrollTime="06:00:00";
	private String minTime="04:00:00";
	private String maxTime="20:00:00";
	private String locale="en";
	private String timeZone="";
	private String clientTimeZone="local";
	private String columnHeaderFormat="";

    @PostConstruct
	public void init() {
		eventModel = new DefaultScheduleModel();

		DefaultScheduleEvent event = new DefaultScheduleEvent("Champions League Match", previousDay8Pm(), previousDay11Pm());
		event.setDescription("Team A vs. Team B");
		eventModel.addEvent(event);

		event = new DefaultScheduleEvent("Birthday Party", today1Pm(), today6Pm());
		event.setDescription("Aragon");
		event.setOverlapAllowed(true);
		eventModel.addEvent(event);

		event = new DefaultScheduleEvent("Breakfast at Tiffanys", nextDay9Am(), nextDay11Am());
		event.setDescription("all you can eat");
		event.setOverlapAllowed(true);
		eventModel.addEvent(event);

		event = new DefaultScheduleEvent("Plant the new garden stuff", theDayAfter3Pm(), fourDaysLater3pm());
		event.setDescription("Trees, flowers, ...");
		eventModel.addEvent(event);

		DefaultScheduleEvent scheduleEventAllDay=new DefaultScheduleEvent("Holidays (AllDay)", sevenDaysLater0am(), eightDaysLater0am());
		scheduleEventAllDay.setDescription("sleep as long as you want");
		scheduleEventAllDay.setAllDay(true);
		eventModel.addEvent(scheduleEventAllDay);

		lazyEventModel = new LazyScheduleModel() {
			
			@Override
			public void loadEvents(LocalDateTime start, LocalDateTime end) {
				for (int i=1; i<=5; i++) {
					LocalDateTime random = getRandomDateTime(start);
					addEvent(new DefaultScheduleEvent("Lazy Event " + i, random, random.plusHours(3)));
				}
			}
		};
	}
	
	public LocalDateTime getRandomDateTime(LocalDateTime base) {
		LocalDateTime dateTime = base.withMinute(0).withSecond(0).withNano(0);
		return dateTime.plusDays(((int) (Math.random()*30)));
	}
	

	public ScheduleModel getEventModel() {
		return eventModel;
	}
	
	public ScheduleModel getLazyEventModel() {
		return lazyEventModel;
	}

	private LocalDateTime previousDay8Pm() {
    	return LocalDateTime.now().minusDays(1).withHour(20).withMinute(0).withSecond(0).withNano(0);
	}
	
	private LocalDateTime previousDay11Pm() {
		return LocalDateTime.now().minusDays(1).withHour(23).withMinute(0).withSecond(0).withNano(0);
	}
	
	private LocalDateTime today1Pm() {
		return LocalDateTime.now().withHour(13).withMinute(0).withSecond(0).withNano(0);
	}
	
	private LocalDateTime theDayAfter3Pm() {
		return LocalDateTime.now().plusDays(1).withHour(15).withMinute(0).withSecond(0).withNano(0);
	}

	private LocalDateTime today6Pm() {
		return LocalDateTime.now().withHour(18).withMinute(0).withSecond(0).withNano(0);
	}
	
	private LocalDateTime nextDay9Am() {
		return LocalDateTime.now().plusDays(1).withHour(9).withMinute(0).withSecond(0).withNano(0);
	}
	
	private LocalDateTime nextDay11Am() {
		return LocalDateTime.now().plusDays(1).withHour(11).withMinute(0).withSecond(0).withNano(0);
	}
	
	private LocalDateTime fourDaysLater3pm() {
		return LocalDateTime.now().plusDays(4).withHour(15).withMinute(0).withSecond(0).withNano(0);
	}

	private LocalDateTime sevenDaysLater0am() {
		return LocalDateTime.now().plusDays(7).withHour(0).withMinute(0).withSecond(0).withNano(0);
	}

	private LocalDateTime eightDaysLater0am() {
		return LocalDateTime.now().plusDays(7).withHour(0).withMinute(0).withSecond(0).withNano(0);
	}

	public ScheduleEvent getEvent() {
		return event;
	}

	public void setEvent(ScheduleEvent event) {
		this.event = event;
	}
	
	public void addEvent() {
    	if (event.isAllDay()) {
    		//see https://github.com/primefaces/primefaces/issues/1164
    		if (event.getStartDate().toLocalDate().equals(event.getEndDate().toLocalDate())) {
				event.setEndDate(event.getEndDate().plusDays(1));
			}
		}

		if(event.getId() == null)
			eventModel.addEvent(event);
		else
			eventModel.updateEvent(event);
		
		event = new DefaultScheduleEvent();
	}
	
	public void onEventSelect(SelectEvent<ScheduleEvent> selectEvent) {
		event = selectEvent.getObject();
	}
	
	public void onDateSelect(SelectEvent<LocalDateTime> selectEvent) {
		event = new DefaultScheduleEvent("", selectEvent.getObject(), selectEvent.getObject());
	}
	
	public void onEventMove(ScheduleEntryMoveEvent event) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event moved", "Delta:" + event.getDeltaAsDuration());
		
		addMessage(message);
	}
	
	public void onEventResize(ScheduleEntryResizeEvent event) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event resized", "Start-Delta:" + event.getDeltaStartAsDuration() + ", End-Delta: " + event.getDeltaEndAsDuration());
		
		addMessage(message);
	}
	
	private void addMessage(FacesMessage message) {
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public boolean isShowWeekends() {
		return showWeekends;
	}

	public void setShowWeekends(boolean showWeekends) {
		this.showWeekends = showWeekends;
	}

	public boolean isTooltip() {
		return tooltip;
	}

	public void setTooltip(boolean tooltip) {
		this.tooltip = tooltip;
	}

	public boolean isAllDaySlot() {
		return allDaySlot;
	}

	public void setAllDaySlot(boolean allDaySlot) {
		this.allDaySlot = allDaySlot;
	}

	public String getTimeFormat() {
		return timeFormat;
	}

	public void setTimeFormat(String timeFormat) {
		this.timeFormat = timeFormat;
	}

	public String getSlotDuration() {
		return slotDuration;
	}

	public void setSlotDuration(String slotDuration) {
		this.slotDuration = slotDuration;
	}

	public String getSlotLabelInterval() {
		return slotLabelInterval;
	}

	public void setSlotLabelInterval(String slotLabelInterval) {
		this.slotLabelInterval = slotLabelInterval;
	}

	public String getScrollTime() {
		return scrollTime;
	}

	public void setScrollTime(String scrollTime) {
		this.scrollTime = scrollTime;
	}

	public String getMinTime() {
		return minTime;
	}

	public void setMinTime(String minTime) {
		this.minTime = minTime;
	}

	public String getMaxTime() {
		return maxTime;
	}

	public void setMaxTime(String maxTime) {
		this.maxTime = maxTime;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public String getClientTimeZone() {
		return clientTimeZone;
	}

	public void setClientTimeZone(String clientTimeZone) {
		this.clientTimeZone = clientTimeZone;
	}

	public String getColumnHeaderFormat() {
		return columnHeaderFormat;
	}

	public void setColumnHeaderFormat(String columnHeaderFormat) {
		this.columnHeaderFormat = columnHeaderFormat;
	}
}
