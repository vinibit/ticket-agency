package com.packtpub.as7development.chapter4.service;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class PollerBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	boolean pollingActive = true;
	
	public boolean isPollingActive() {
		return pollingActive;
	}
	
	public void setPollingActive(boolean pollingActive) {
		this.pollingActive = pollingActive;
	}
}
