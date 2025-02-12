package com.barunsw.ojt.mjg.day14;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.common.AddressBookInterface;
import com.barunsw.ojt.vo.AddressVo;
import com.barunsw.ojt.constants.Gender;

public class SocketAddressBookImpl implements AddressBookInterface {
    private static final Logger LOGGER = LogManager.getLogger(SocketAddressBookImpl.class);

    private Socket clientSocket;
    
    private BufferedReader reader;
    private BufferedWriter writer;

    public SocketAddressBookImpl(String host, int port) throws Exception {
        clientSocket = new Socket(host, port);
        reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
    }

/*
	@Override
	public List<AddressVo> selectAddressList(AddressVo addressVo) {
//		String command = String.format("SELECT\n");
		String command = "SELECT\n";

		try {
			writer.write(command);
		}
		catch (IOException e) {
			LOGGER.error("출력 스트림에 명령어를 작성하는 데 실패했습니다. 명령어: {}", command, e);
		}
		try {
			writer.flush();
		}
		catch (IOException e) {
			LOGGER.error("출력 스트림에 명령어를 작성한 후 플러시하는 데 실패했습니다. 명령어: {}", command, e);

		}

		StringBuffer buf = new StringBuffer();

		String readLine = null;
		try {
			while ((readLine = reader.readLine()) != null) {
				buf.append(readLine + "\n");
			}
		}
		catch (IOException e) {
			LOGGER.error("입력 스트림에서 읽는 도중 실패했습니다. 클라이언트가 연결을 끊었거나 네트워크 오류가 발생했을 수 있습니다.", e);
			e.printStackTrace();
		}

		LOGGER.debug("received:" + readLine);

		List<AddressVo> list = null;

		// buf의 내용을 list에 넣는다.

		return list;
	}
*/
    
    @Override
    public List<AddressVo> selectAddressList(AddressVo addressVo) {
    	List<AddressVo> list = new ArrayList<>();
        String command = "SELECT:\n";  // SELECT:를 명확히 보냄

        try {
            writer.write(command);
            writer.flush();
        }
        catch (IOException e) {
            LOGGER.error("출력 스트림에 명령어를 작성하는 데 실패했습니다. 명령어: {}", command, e);
            return list;
        }

        try {
            String readLine;
            while ((readLine = reader.readLine()) != null) {
                if ("NO_RESULT".equals(readLine)) {
                    return list;  // 결과 없음
                }
                if ("END_OF_DATA".equals(readLine)) {
                    break;  // 서버에서 데이터가 끝났다는 신호를 받으면 루프 종료
                }

                String[] data = readLine.split(",");
                if (data.length == 5) {
                    AddressVo vo = new AddressVo();
                    vo.setName(data[0]);
                    vo.setAge(Integer.parseInt(data[1]));
                    vo.setGender(Gender.toGender(data[2]));
                    vo.setPhone(data[3]);
                    vo.setAddress(data[4]);
                    list.add(vo);
                }
            }
        }
        catch (IOException e) {
            LOGGER.error("입력 스트림에서 읽는 도중 실패했습니다.", e);
        }
		catch (Exception e) {
			e.printStackTrace();
		}

        return list;
    }
    
    @Override
    public int insertAddress(AddressVo addressVo) throws Exception {
        String command = String.format("INSERT:NAME=%s,AGE=%s,GENDER=%s,ADDRESS=%s,PHONE=%s\n",
            addressVo.getName()
            , addressVo.getAge()
            , addressVo.getGender()
            , addressVo.getAddress()
            , addressVo.getPhone()
        );

        writer.write(command);
        writer.flush();
        return 0;
    }

    @Override
    public int updateAddress(AddressVo addressVo) throws Exception {
        String command = String.format("UPDATE:SEQ=%d,NAME=%s,AGE=%s,GENDER=%s,ADDRESS=%s,PHONE=%s\n",
            addressVo.getSeq()
            , addressVo.getName()
            , addressVo.getAge()
            , addressVo.getGender()
            , addressVo.getAddress()
            , addressVo.getPhone()
        );

        writer.write(command);
        writer.flush();
        return 0;
    }

    @Override
    public int deleteAddress(AddressVo addressVo) throws Exception {
        String command = String.format("DELETE:SEQ=%d\n", addressVo.getSeq());
        
        writer.write(command);
        writer.flush();
        return 0;
    }
/*
	public void close() {

	}
*/

}
