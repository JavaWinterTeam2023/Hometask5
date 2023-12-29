package Model;

import Services.ResourceBuffer;

public class Battery implements Runnable{

	private ResourceBuffer resourceBuffer;
	private int id;
	
	public Battery(int id, ResourceBuffer resourceBuffer) {
		this.resourceBuffer = resourceBuffer;
		this.id = id;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		resourceBuffer.add(this.id);
	}
}
