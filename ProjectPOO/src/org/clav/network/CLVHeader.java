package org.clav.network;

public enum CLVHeader {
	SIG,//Network only
	MSG,//Applicative
	STR,//Debug only
	ACK,//Network only
	END,//Applicative and network
	CHI,//Applicative, chat initiation
	REJ,//Applicative, pseudo rejected
	ERR;
}
