package org.clav.network;

import static org.clav.utils.Constants.SIGACT_HEADER;

public class ActivitySignalProtocol extends Protocol {
	public static long SIGNAL_PERIOD = 10000;
	public ActivitySignalProtocol(ActivitySignalProtocolInit protocolInit) {
		super(protocolInit);
	}

	@Override
	public ActivitySignalProtocolInit getProtocolInit() {
		return (ActivitySignalProtocolInit) super.getProtocolInit();
	}

	@Override
	public void run() {
		while(true){
			String signal = SIGACT_HEADER+getProtocolInit().getUserManager().getMainUser().getIdentifier()+"--"+getProtocolInit().getUserManager().getMainUser().getPseudo();
			byte[] buf = signal.getBytes();
			System.out.println("[UDP]ActivitySignalProtocol sending activity report");
			getRelatedNetworkManager().broadcast(buf);

			try {
				Thread.sleep(SIGNAL_PERIOD);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
