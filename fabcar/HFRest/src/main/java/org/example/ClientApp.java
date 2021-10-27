/*
SPDX-License-Identifier: Apache-2.0
*/

package org.example;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Network;
import org.hyperledger.fabric.gateway.Wallet;
import org.hyperledger.fabric.gateway.Wallets;

public class ClientApp {
	
	Contract contract;
	Gateway.Builder builder;
	Wallet wallet;
	Path networkConfigPath;

	static {
		System.setProperty("org.hyperledger.fabric.sdk.service_discovery.as_localhost", "true");
	}
	
	public ClientApp() {
		try {
			Path walletPath = Paths.get("/Users/raymanan11/Desktop/CSULB/CECS406/HF/fabcar/HFRest/wallet");
			wallet = Wallets.newFileSystemWallet(walletPath);
			// load a CCP
			networkConfigPath = Paths.get("/Users/raymanan11/Desktop/CSULB/CECS406/HF/test-network/organizations/peerOrganizations/org1.example.com/connection-org1.yaml");

			builder = Gateway.createBuilder();
			builder.identity(wallet, "appUser").networkConfig(networkConfigPath).discovery(true);
		}
		catch (Exception e) {
			System.out.println("Error: " + e);
		}
	}
	
	public String getAllAirlineParts() {
		try {
			// create a gateway connection
			try (Gateway gateway = builder.connect()) {

				// get the network and contract
				Network network = gateway.getNetwork("mychannel");
				contract = network.getContract("fabcar");
				
				byte[] result;

				result = contract.evaluateTransaction("QueryAllAirlineParts");
				return new String(result);
			}
				
		}
		catch (Exception e) {
			System.out.println("Error mania: " + e);
			return "Error";
		}
	}
	
	public String queryAirlinePart(String airlinePart) {
		try {

			// create a gateway connection
			try (Gateway gateway = builder.connect()) {

				// get the network and contract
				Network network = gateway.getNetwork("mychannel");
				contract = network.getContract("fabcar");
				
				byte[] result;

				result = contract.evaluateTransaction("QueryAirlinePart", airlinePart);
				return new String(result);
			}
				
		}
		catch (Exception e) {
			System.out.println("Error mania: " + e);
			return "Error";
		}
	}
	
	public String changePartOwner(String airlinePart, String newOwner) {
		try {

			// create a gateway connection
			try (Gateway gateway = builder.connect()) {

				// get the network and contract
				Network network = gateway.getNetwork("mychannel");
				contract = network.getContract("fabcar");
				
				byte[] result;

				result = contract.evaluateTransaction("ChangeAirlinePartOwner", airlinePart, newOwner);
				return new String(result);
			}
				
		}
		catch (Exception e) {
			System.out.println("Error mania: " + e);
			return "Error";
		}
	}
	
	
}
