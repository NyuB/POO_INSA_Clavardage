package org.clav.network.protocolsimpl.udp;

import org.clav.network.CLVHeader;
import org.clav.network.CLVPacket;
import org.clav.network.Protocol;
import org.clav.utils.Serializer;
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
			CLVPacket packet = new CLVPacket(CLVHeader.SIG,getRelatedNetworkManager().getAppHandler().getMainUser());
			byte[] buf = Serializer.toBytes(packet);
			getRelatedNetworkManager().broadcast(buf);
			try {
				Thread.sleep(ACTIVITY_SIGNAL_DELAY);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
