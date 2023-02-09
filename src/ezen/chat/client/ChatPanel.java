package ezen.chat.client;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JList;
import javax.swing.JOptionPane;

public class ChatPanel extends Panel {
	Panel connectPanel, inputPanel;
	Label nicknameLabel;
	TextField nickNameTF, inputTF;
	Button connectButton, sendButton;

	TextArea messageTA;
	JList<String> userList;
	
	// 전화기 역할
	ChatClient chatClient;
	String nickName;

	public ChatPanel() {
		this.connectPanel = new Panel();
		inputPanel = new Panel();
		nicknameLabel = new Label("대화명: ");
		nickNameTF = new TextField("사용하고자 하는 대화명 입력");
		inputTF = new TextField();
		connectButton = new Button(" 연  결 ");
		sendButton = new Button(" 보내기 ");
		messageTA = new TextArea(10, 20);

		String[] list = { "김재훈", "이대한", "김정석", "박찬우" };
		userList = new JList<String>(list);

	}

	// 컴포넌트 배치
	public void initLayout() {
		// 레이아웃 담당 매니저 설정
		setLayout(new BorderLayout());

		connectPanel.setLayout(new BorderLayout());
		connectPanel.add(nicknameLabel, BorderLayout.WEST);
		connectPanel.add(nickNameTF, BorderLayout.CENTER);
		connectPanel.add(connectButton, BorderLayout.EAST);

		inputPanel.setLayout(new BorderLayout());
		inputPanel.add(inputTF, BorderLayout.CENTER);
		inputPanel.add(sendButton, BorderLayout.EAST);

		add(connectPanel, BorderLayout.NORTH);
		add(messageTA, BorderLayout.CENTER);
		add(userList, BorderLayout.EAST);
		add(inputPanel, BorderLayout.SOUTH);

	}

	// 컴포넌트 이벤트 처리
	public void eventRegist() {
		nickNameTF.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				nickNameTF.setText("");
			}
		});
		
		nickNameTF.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				connect();
			}
		});

		connectButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				connect();
			}
		});

		// 텍스트필드 액션이벤트 처리
		inputTF.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				sendMessage();

			}
		});

		// 보내기 버튼 액션이벤트 처리
		sendButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				sendMessage();

			}
		});

	}

	// 서버 연결
	private void connect() {
		// 유효성 검증

		nickName = nickNameTF.getText();

		chatClient = new ChatClient(this); // 챗클과 양방향
		// 서버 연결
		try {
			chatClient.connectServer();
			chatClient.receiveMessage();
			// 대화명 비활성화
			nickNameTF.setEnabled(false);
			nickNameTF.setEditable(false);
			// 서버에 대화 참여 메세지를 전송
			// chatClient.sendMessage("☆☆☆☆☆["+nickName+"]님이 대화에 참여하셨습니다.☆☆☆☆☆");
			// 메세지 종류 | 메세지 보내는 사람 닉네임 | ... (이게 문자열.. 스트링토크나이저 처럼.)
			// 사용자 정의 프로토콜 설계
			// "메세지종류*대화명"
			// "CONNECT*방그리"
			chatClient.sendMessage("CONNECT*"+nickName);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "관리자에게 문의하여주세요.","서버 연결 실패", JOptionPane.ERROR_MESSAGE);
		}


	}

	// 메세지 전송 처리
	private void sendMessage() {
		String inputMessage = inputTF.getText();
		// 입력메세지 검증
		if(Validator.isEmpty(inputMessage)) {
			return;
		}
		
		inputTF.setText("");
		// 서버로 전송
		try {
			chatClient.sendMessage("["+nickName+"] : " + inputMessage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
//		messageTA.append(inputMessage + "\n");
	}

	// 서버로부터 수신한 메세지를 출력
	public void appendMessage(String message) {
		messageTA.append(message+"\n");
	}

}
