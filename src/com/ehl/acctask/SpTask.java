package com.ehl.acctask;

import java.util.TimerTask;

public class SpTask extends TimerTask {

	@Override
	public void run() {
		new AccSp().spAcc(null);
	}

}
