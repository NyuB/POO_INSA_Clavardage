package org.clav.network;

import java.io.Serializable;

public enum CLVHeader implements Serializable {
	SIG,//Network only
	ACK,//Network only
	STR,//Debug only
	END,//Applicative and network
	MSG,//Applicative, chat message
	CHI,//Applicative, chat initiation
	IMG,//Applicative, image
	UNK,//Applicative, unknown chatcode notification
	REJ,//Applicative, pseudo rejected
	REQ,//Applicative, chat update request
	NOT,//Server rerouting service
	SUB,//Server subscription service
	PUB,//Server activity signal service

	ERR;
}
