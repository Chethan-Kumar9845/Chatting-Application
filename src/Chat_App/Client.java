package Chat_App;


import javax.swing.*;
import java.util.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.net.*;
import java.io.*;

public class Client implements ActionListener{
	JTextField text;
	 JPanel msg;
	static Box verticle=Box.createVerticalBox();
	static JFrame f=new JFrame();
	static DataOutputStream dout;
	
	Client()
	{
		f.setLayout(null);
		f.setSize(450,700);
		f.setLocation(750,40);
		f.setUndecorated(true);
		f.getContentPane().setBackground(Color.WHITE);
		
		//-----top panel------
		JPanel p=new JPanel();
		p.setBackground(new Color(25,159,236));
		p.setBounds(0, 0, 450, 70);
		p.setLayout(null);
		f.add(p);
		
		//back-button
		ImageIcon i1=new ImageIcon(ClassLoader.getSystemResource("back1.png"));
		Image i2=i1.getImage().getScaledInstance(30, 30,Image.SCALE_SMOOTH);
		ImageIcon i3=new ImageIcon(i2);
		JLabel back=new JLabel(i3);
		back.setBounds(5, 20, 30, 30);
		p.add(back);
		
		back.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent me) {
				f.setVisible(false);
			}
		});
		
		//profile
		ImageIcon i4=new ImageIcon(ClassLoader.getSystemResource("profile2.png"));
		Image i5=i4.getImage().getScaledInstance(50, 50,Image.SCALE_SMOOTH);
		ImageIcon i6=new ImageIcon(i5);
		JLabel profile=new JLabel(i6);
		profile.setBounds(35, 10, 50, 50);
		p.add(profile);
		
		//Profile-name
		JLabel name=new JLabel("AB de Villiers");
		name.setBounds(90, 18, 100, 18);
		name.setForeground(Color.BLACK);
		name.setFont(new Font("Times new Roman",Font.BOLD,18));
		p.add(name);
		
		//status
		JLabel status=new JLabel("Active Now");
		status.setBounds(90, 40, 100, 14);
		status.setForeground(Color.BLACK);
		status.setFont(new Font("Times new Roman",Font.BOLD,14));
		p.add(status);
		
		//video call
		ImageIcon i7=new ImageIcon(ClassLoader.getSystemResource("video1.png"));
		Image i8=i7.getImage().getScaledInstance(30, 30,Image.SCALE_SMOOTH);
		ImageIcon i9=new ImageIcon(i8);
		JLabel video=new JLabel(i9);
		video.setBounds(300, 20, 30, 30);
		p.add(video);
		
		//voice call
		ImageIcon i10=new ImageIcon(ClassLoader.getSystemResource("voice.png"));
		Image i11=i10.getImage().getScaledInstance(30, 30,Image.SCALE_SMOOTH);
		ImageIcon i12=new ImageIcon(i11);
		JLabel voice=new JLabel(i12);
		voice.setBounds(350, 20, 30, 30);
		p.add(voice);
		
		//more_option
		ImageIcon i13=new ImageIcon(ClassLoader.getSystemResource("more_option1.png"));
		Image i14=i13.getImage().getScaledInstance(27, 27,Image.SCALE_SMOOTH);
		ImageIcon i15=new ImageIcon(i14);
		JLabel more=new JLabel(i15);
		more.setBounds(400, 22, 27, 27);
		p.add(more);
		
		//---------Message Panel----------
		msg=new JPanel();
		msg.setBounds(5, 75, 440, 550);
		msg.setBackground(Color.WHITE);
		msg.setLayout(new BorderLayout());
		msg.add(verticle, BorderLayout.PAGE_START);
		f.add(msg);
		
		//---Text Send
		text=new JTextField();
		text.setBounds(5,630,330,30);
		text.setFont(new Font("Times new Roman",Font.PLAIN,16));
		text.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
		f.add(text);
		
		JButton send=new JButton("Send");
		send.setBounds(340, 630, 105, 30);
		send.setForeground(Color.BLACK);
		send.setBackground(new Color(25,159,236));
		send.setFont(new Font("Times new Roman",Font.PLAIN,16));
		send.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
		f.add(send);
		
		
		send.addActionListener(this);		
		
		
		
		f.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent ae)
	{
		try {
		String str=text.getText();
		
		JPanel p1=formateLabel(str);
		
		JPanel right=new JPanel(new BorderLayout());
		right.add(p1,BorderLayout.LINE_END);
		
		verticle.add(right);
		verticle.add(Box.createVerticalStrut(15));
		
		dout.writeUTF(str);
 
		
		text.setText("");
		
		f.repaint();
		f.invalidate();
		f.validate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static JPanel formateLabel(String str)
	{
		JLabel label=new JLabel("<html><p style='width:150px'>"+str+"</p></html>");
		label.setFont(new Font("Tahoma",Font.PLAIN,16));
		label.setBackground(new Color(95,238,109));
		label.setOpaque(true);
		label.setBorder(new EmptyBorder(15,15,15,50));
		
		JPanel p=new JPanel();
		p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));
		p.add(label);
		
		Calendar cd=Calendar.getInstance();
		SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
		
		JLabel time=new JLabel();
		time.setText(sdf.format(cd.getTime()));
		
		p.add(time);
		
		return p;
	}
	public static void main(String args[])
	{
		new Client();
		try {
			Socket s=new Socket("127.0.0.1",1234);
			DataInputStream din=new DataInputStream(s.getInputStream());
			dout=new DataOutputStream(s.getOutputStream());
			
			while(true)
			{
				String msg=din.readUTF();
				
				JPanel panel=formateLabel(msg);
				
				JPanel left=new JPanel(new BorderLayout());
				left.add(panel,BorderLayout.LINE_START);
				
				
				
				verticle.add(left);
				f.validate();
				
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
