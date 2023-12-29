package Model;

import java.util.Random;

import Services.ResourceBuffer;

public class EnergySource implements Runnable{

	private ResourceBuffer resourceBuffer;
	private String name;
	private Random random = new Random();
	
	
	public EnergySource (String name, ResourceBuffer resourceBuff) {
		this.name = name;
		this.resourceBuffer = resourceBuff;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		int value = random.nextInt(10);
		try {
			Thread.sleep(value*1000); // Charging time
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		resourceBuffer.free(this.name);
	}
	
	
	
}
