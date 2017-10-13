import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;
import java.io.IOException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Scanner;
import javax.swing.JOptionPane;
public class YtAlarmTest
{
	
	public static void main (String [] args)
	{	
		//Scanner read = new Scanner(System.in);
		//System.out.println("When would you like to wake up? (enter in this format: yyyy/MM/dd HH:mm)");
		//String wakeUpTime = read.nextLine();
		String dialogBox = "When would you like to wake up? (enter in this format: HH:mm)";
		String wakeUpTime = JOptionPane.showInputDialog(dialogBox);
		YtAlarmTest test = new YtAlarmTest();
		test.start(wakeUpTime);
	}

	String link = "https://www.youtube.com/watch?v=uuNHWdhjDXU";
	Timer tmr = new Timer();
	TimerTask tsk = new TimerTask()
	{
		public void run()
		{
			try
			{
			if (Desktop.isDesktopSupported())
				{
					Desktop.getDesktop().browse(new URI(link));
				}
			}
			catch (URISyntaxException e)
			{
				System.out.println("exception 1");
			}
			catch (IOException e)
			{
				System.out.println("exception 2");
			}
			catch (Exception e)
			{
				System.out.println("exception 3");
			}
		}
		
	};
	
	public YtAlarmTest()
	{
		Timer timer = tmr;
		TimerTask task = tsk;
		String url= link;
	}
		
	public void start(String hrmin)
	{
		long delay = findDelay(hrmin);
		tmr.schedule(tsk, delay);
	}
	
	public long findDelay(String sdfAlarmHrMin)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		Date alarm = new Date();
		try
		{
			alarm = sdf.parse(adjustAlarm(sdfAlarmHrMin));
		}
		catch (ParseException e)
		{
			System.out.println("exception 4");
		}
		long current = System.currentTimeMillis();
		long delay = alarm.getTime() - current;
		return delay;
	}
	
	public String adjustAlarm(String hourMinute)
	{
		Calendar crrnt = Calendar.getInstance();
		SimpleDateFormat  sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		String adjustedAlarm = "";
		int yr = crrnt.get(Calendar.YEAR);
		int mnth = crrnt.get(Calendar.MONTH);
		int dy = crrnt.get(Calendar.DAY_OF_MONTH);
		
		String defaultDate = crrnt.get(Calendar.YEAR) + "/" + crrnt.get(Calendar.MONTH) + "/" + crrnt.get(Calendar.DAY_OF_MONTH) + " " + hourMinute;
		System.out.println(defaultDate);
		Date defaultDateAlarm = new Date();
		
		try
		{
			defaultDateAlarm = sdf.parse(defaultDate);
		}
		catch (ParseException e)
		{
			System.out.println("Exception");
		}
	
		if (defaultDateAlarm.getTime() - System.currentTimeMillis() < 0)
		{
			if (dy == 29 && mnth == 1)
			{
				crrnt.add(Calendar.MONTH,1);
				crrnt.set(Calendar.DAY_OF_MONTH,1);
			}
			else if (dy == 28 && mnth == 1)
			{
				crrnt.add(Calendar.MONTH,1);
				crrnt.set(Calendar.DAY_OF_MONTH,1);
			}
			else if (dy == 31)
			{
				if (mnth == 0 || mnth == 2 ||mnth == 4 || mnth == 6 ||  mnth == 7 || mnth == 9) //months with 31 days except December
				{
					crrnt.add(Calendar.MONTH,1);
					crrnt.set(Calendar.DAY_OF_MONTH,1);
				}
				else if (mnth == 11) //December
				{
					crrnt.set(Calendar.MONTH,1);
					crrnt.set(Calendar.DAY_OF_MONTH,1);
				}
			}
			else if (dy == 30)
			{
				if (mnth == 3 || mnth == 5 ||mnth == 8 || mnth == 10) //months with 30 
				{
					crrnt.add(Calendar.MONTH,1);
					crrnt.set(Calendar.DAY_OF_MONTH,1);
				}
				else if (mnth == 11) //December
				{
					crrnt.set(Calendar.MONTH,0);
					crrnt.set(Calendar.DAY_OF_MONTH,1);
					crrnt.add(Calendar.YEAR,1);
				}
			}
			else
			{
				crrnt.add(Calendar.DAY_OF_MONTH,1);
			}
			adjustedAlarm = crrnt.get(Calendar.YEAR) + "/" + crrnt.get(Calendar.MONTH) + "/" + crrnt.get(Calendar.DAY_OF_MONTH) + " " + hourMinute;
		}
		else return defaultDate;
		return adjustedAlarm;
	}
	
}