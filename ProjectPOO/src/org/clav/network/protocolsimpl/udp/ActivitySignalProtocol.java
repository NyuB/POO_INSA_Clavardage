package org.clav.network.protocolsimpl.udp;

import org.clav.network.CLVPacket;
import org.clav.network.CLVPacketFactory;
import org.clav.network.Protocol;
import org.clav.utils.Serializer;

import static org.clav.utils.constants.DelayConstants.ACTIVITY_SIGNAL_DELAY;

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
		boolean over = false;
		try {
			while (true) {
				CLVPacket packet = CLVPacketFactory.gen_SIG(getRelatedNetworkManager().getAppHandler().getMainUser());

				byte[] buf = Serializer.toBytes(packet);
				getRelatedNetworkManager().broadcast(buf);

				Thread.sleep(ACTIVITY_SIGNAL_DELAY);
			}
		} catch (InterruptedException e) {
			System.out.println("[UDP]Interrupting activity signal");
		}

	}
}
