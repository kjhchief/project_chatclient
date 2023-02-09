package ezen.chat.client;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ezenTalk {

	 public static void setCenterScreen(Frame frame) { //언제든지 사용할 수 있게 static로 만듦
	      Toolkit toolkit = Toolkit.getDefaultToolkit();
	      int screenwidth = toolkit.getScreenSize().width;
	      int screenHeight = toolkit.getScreenSize().height;

	      int centerX = (screenwidth - frame.getWidth()) / 2;
	      int centerY = (screenHeight - frame.getHeight()) / 2;
	      frame.setLocation(centerX, centerY);
	   }
	
	public static void main(String[] args) {
		Frame frame = new Frame("::: ezenTalk :::");
		ChatPanel chatPanel = new ChatPanel();
		chatPanel.initLayout();
		chatPanel.eventRegist();
		frame.add(chatPanel, BorderLayout.CENTER);
		frame.setSize(400, 550);
//		setFullScreen(frame);
		setCenterScreen(frame);
		
		frame.setVisible(true);
		
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		

	}

}
