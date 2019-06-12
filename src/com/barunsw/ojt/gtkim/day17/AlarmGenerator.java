package com.barunsw.ojt.gtkim.day17;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.constants.AlarmId;
import com.barunsw.ojt.constants.Severity;
import com.barunsw.ojt.vo.AlarmVo;

public class AlarmGenerator extends Thread {
	private static final Logger LOGGER = LogManager.getLogger(AlarmGenerator.class);
	
	private final int DURATION = 5_000;
	
	private boolean runFlag; 
	
	private SimpleDateFormat simpleDataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private List<AlarmVo> alarmData = new ArrayList<AlarmVo>();
	
	public AlarmGenerator() {
		try { 
			initAlarmData();
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
	
	private void initAlarmData() {
		// 미리 약속된 기본 데이터 값
		AlarmVo oneAlarm = new AlarmVo();
		oneAlarm.setAlarmId(AlarmId.ALARM_ID_BOARD_ALARM);
		oneAlarm.setAlarmMsg("BOARD ALARM OCCUR");
		oneAlarm.setSeverity(Severity.MAJOR);
		
		alarmData.add(oneAlarm);

		oneAlarm = new AlarmVo();
		oneAlarm.setAlarmId(AlarmId.ALARM_ID_BOARD_ALARM);
		oneAlarm.setAlarmMsg("BOARD ALARM CLEAR");
		oneAlarm.setSeverity(Severity.NORMAL);

		alarmData.add(oneAlarm);
		
		oneAlarm = new AlarmVo();
		oneAlarm.setAlarmId(AlarmId.ALARM_ID_BOARD_ALARM);
		oneAlarm.setAlarmMsg("BOARD ALARM CRITICAL");
		oneAlarm.setSeverity(Severity.CRITICAL);

		alarmData.add(oneAlarm);
		
		oneAlarm = new AlarmVo();
		oneAlarm.setAlarmId(AlarmId.ALARM_ID_BOARD_ALARM);
		oneAlarm.setAlarmMsg("BOARD ALARM MINOR");
		oneAlarm.setSeverity(Severity.MINOR);

		alarmData.add(oneAlarm);

		oneAlarm = new AlarmVo();
		oneAlarm.setAlarmId(AlarmId.ALARM_ID_PORT_ALARM);
		oneAlarm.setAlarmMsg("PORT ALARM OCCUR");
		oneAlarm.setSeverity(Severity.MAJOR);

		alarmData.add(oneAlarm);

		oneAlarm = new AlarmVo();
		oneAlarm.setAlarmId(AlarmId.ALARM_ID_PORT_ALARM);
		oneAlarm.setAlarmMsg("PORT ALARM CLEAR");
		oneAlarm.setSeverity(Severity.NORMAL);

		alarmData.add(oneAlarm);

		oneAlarm = new AlarmVo();
		oneAlarm.setAlarmId(AlarmId.ALARM_ID_ENV_ALARM);
		oneAlarm.setAlarmMsg("ENV ALARM OCCUR");
		oneAlarm.setSeverity(Severity.CRITICAL);

		alarmData.add(oneAlarm);

		oneAlarm = new AlarmVo();
		oneAlarm.setAlarmId(AlarmId.ALARM_ID_ENV_ALARM);
		oneAlarm.setAlarmMsg("ENV ALARM CLEAR");
		oneAlarm.setSeverity(Severity.NORMAL);

		alarmData.add(oneAlarm);		
	}
	
	private AlarmVo generateAlarm() {
		AlarmVo alarmVo = alarmData.get((int)(Math.random() * alarmData.size()));
		
		alarmVo.setEventTime(simpleDataFormat.format(Calendar.getInstance().getTime()));
		
		if (alarmVo.getAlarmId().equals(AlarmId.ALARM_ID_BOARD_ALARM)) {
			int boardId = (int)(Math.random() * 20);
			
			alarmVo.setAlarmLoc(String.format("BOARD_ID=%s", boardId));
		}
		
		LOGGER.debug("generate alarm:" + alarmVo);
		
		return alarmVo;
	}
	
	@Override 
	public void run() {
		runFlag = true;
		
		while (runFlag) {
			try {
				Thread.sleep(DURATION);
			}
			catch (InterruptedException ie) {
				LOGGER.error(ie.getMessage(), ie);
			}
			
			AlarmVo alarmVo = generateAlarm();
			
			ServerMain.serverImpl.sendAlarm(alarmVo);
		}
	}
	
	public void close() {
		runFlag = false;
	}
}
