package com.barunsw.ojt.sjcha.day17;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.sjcha.day17.AlarmId;
import com.barunsw.ojt.sjcha.day17.Severity;
import com.barunsw.ojt.sjcha.day17.AlarmVo;

public class AlarmGenerator extends Thread {
	private static final Logger LOGGER = LogManager.getLogger(AlarmGenerator.class);

	private final int DURATION = 1000;

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
				LOGGER.error(ie.getMessage(), ie);
			}

			AlarmVo alarmVo = generateAlarm();

			LOGGER.debug("AlarmGenerator class run() after alarm data : " + alarmVo);

			ServerMain.server.sendAlarm(alarmVo);
		}
	}

	private void initAlarmData() {
		AlarmVo oneAlarm = new AlarmVo();

		oneAlarm.setAlarmId(AlarmId.ALARM_ID_BOARD_ALARM);
		oneAlarm.setAlarmMsg("BOARD ALARM OCCUR");
		oneAlarm.setSeverity((int)(Math.random() * 4) / 3);

		alarmData.add(oneAlarm);

		oneAlarm = new AlarmVo();
		oneAlarm.setAlarmId(AlarmId.ALARM_ID_BOARD_ALARM);
		oneAlarm.setAlarmMsg("BOARD ALARM CLEAR");
		oneAlarm.setSeverity((int)(Math.random() * 4) / 3);

		alarmData.add(oneAlarm);

		oneAlarm = new AlarmVo();
		oneAlarm.setAlarmId(AlarmId.ALARM_ID_PORT_ALARM);
		oneAlarm.setAlarmMsg("PORT ALARM OCCUR");
		oneAlarm.setSeverity((int)(Math.random() * 4) / 3);

		alarmData.add(oneAlarm);

		oneAlarm = new AlarmVo();
		oneAlarm.setAlarmId(AlarmId.ALARM_ID_PORT_ALARM);
		oneAlarm.setAlarmMsg("PORT ALARM CLEAR");
		oneAlarm.setSeverity((int)(Math.random() * 4) / 3);

		alarmData.add(oneAlarm);

		oneAlarm = new AlarmVo();
		oneAlarm.setAlarmId(AlarmId.ALARM_ID_ENV_ALARM);
		oneAlarm.setAlarmMsg("ENV ALARM OCCUR");
		oneAlarm.setSeverity((int)(Math.random() * 4) / 3);

		alarmData.add(oneAlarm);

		oneAlarm = new AlarmVo();
		oneAlarm.setAlarmId(AlarmId.ALARM_ID_ENV_ALARM);
		oneAlarm.setAlarmMsg("ENV ALARM CLEAR");
		oneAlarm.setSeverity((int)(Math.random() * 4) / 3);

		alarmData.add(oneAlarm);
	}

	// time, location 설정.
	private AlarmVo generateAlarm() {
		int alarmIndex = (int)(Math.random() * alarmData.size());

		AlarmVo alarmVo = alarmData.get(alarmIndex);

		// 시간 설정.
		alarmVo.setEventTime(sdf.format(Calendar.getInstance().getTime()));

		int boardId = (int)(Math.random() * 38);

		// 보드의 문제가 생겼을 경우
		if (alarmVo.getAlarmId().equals(AlarmId.ALARM_ID_BOARD_ALARM)) {
			// SRGU 일 경우
			if (boardId == 19) {
				boardId = 18;
			}
			if (boardId == 37) {
				boardId = 36;
			}
			alarmVo.setAlarmLoc(String.format("BOARD_ID=%s", boardId));
		}

		LOGGER.debug("generate alarm data : " + alarmVo);

		return alarmVo;
	}

	public void close() {
		runFlag = false;
	}
}
