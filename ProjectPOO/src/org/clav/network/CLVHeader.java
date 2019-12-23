package org.clav.network;

public enum CLVHeader {
	SIG,//Network only
	ACK,//Network only
	STR,//Debug only
	END,//Applicative and network
	MSG,//Applicative, chat message
	CHI,//Applicative, chat initiation
	REJ,//Applicative, pseudo rejected
	REQ,//Applicative, chat update request
	ERR;
}
