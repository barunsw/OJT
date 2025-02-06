package com.barunsw.ojt.yjkim.day17;

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
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private List<AlarmVo> alarmData = new ArrayList<AlarmVo>();
	
	public AlarmGenerator() {
		try {
			initAlarmData();
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
	
	@Override
	public void run() {
		runFlag = true;
		while (runFlag) {
			try {
				Thread.sleep(DURATION);
			}
			catch (InterruptedException ie) {
			}
			
			AlarmVo alarmVo = generateAlarm();
			
			ServerMain.serverImpl.sendAlarm(alarmVo);
		}
	}
	
	private void initAlarmData() {
		AlarmVo oneAlarm = new AlarmVo();
		oneAlarm.setAlarmId(AlarmId.ALARM_ID_BOARD_ALARM);
		oneAlarm.setAlarmMsg("BOARD ALARM OCCUR");
		oneAlarm.setSeverity(Severity.CRITICAL);
		
		alarmData.add(oneAlarm);

		oneAlarm = new AlarmVo();
		oneAlarm.setAlarmId(AlarmId.ALARM_ID_BOARD_ALARM);
		oneAlarm.setAlarmMsg("BOARD ALARM OCCUR");
		oneAlarm.setSeverity(Severity.MAJOR);

		alarmData.add(oneAlarm);

		oneAlarm = new AlarmVo();
		oneAlarm.setAlarmId(AlarmId.ALARM_ID_BOARD_ALARM);
		oneAlarm.setAlarmMsg("BOARD ALARM OCCUR");
		oneAlarm.setSeverity(Severity.MINOR);

		alarmData.add(oneAlarm);
		
		oneAlarm = new AlarmVo();
		oneAlarm.setAlarmId(AlarmId.ALARM_ID_BOARD_ALARM);
		oneAlarm.setAlarmMsg("BOARD ALARM CLEAR");
		oneAlarm.setSeverity(Severity.NORMAL);

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
		int alarmIndex = (int)(Math.random() * alarmData.size());
		
		AlarmVo alarmVo = alarmData.get(alarmIndex);
		alarmVo.setEventTime(sdf.format(Calendar.getInstance().getTime()));
		int boardId = (int)(Math.random() * 38);

		if (alarmVo.getAlarmId().equals(AlarmId.ALARM_ID_BOARD_ALARM)) {
			if (boardId == 19) {
				boardId = 18;
			} 
			if (boardId == 37) {
				boardId = 36;
			}
			alarmVo.setAlarmLoc(String.format("BOARD_ID=%s", boardId));
		}
		
		LOGGER.debug("generate alarm:" + alarmVo);
		
		return alarmVo;
	}
	
	public void close() {
		runFlag = false;
	}
}
