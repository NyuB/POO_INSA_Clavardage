package org.clav.network.protocolsimpl.udp;

import org.clav.network.Protocol;

import static org.clav.utils.constants.ProtocolConstants.SIGACT_HEADER;
import static org.clav.utils.constants.NetworkConstants.ACTIVITY_SIGNAL_DELAY;

public class ActivitySignalProtocol extends Protocol {
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
			//System.out.println("[UDP]ActivitySignalProtocol sending activity report");
			getRelatedNetworkManager().broadcast(buf);

			try {
				Thread.sleep(ACTIVITY_SIGNAL_DELAY);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
