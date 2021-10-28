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
	
	public Contract contract;
	public Gateway.Builder builder;
	public Wallet wallet;
	public Path networkConfigPath;
	public Network network;

	static {
		System.setProperty("org.hyperledger.fabric.sdk.service_discovery.as_localhost", "true");
	}
	
//	public ClientApp() {
//		try {
//			Path walletPath = Paths.get("/Users/raymanan11/Desktop/CSULB/CECS406/HF/fabcar/HFRest/wallet");
//			wallet = Wallets.newFileSystemWallet(walletPath);
//			// load a CCP
//			networkConfigPath = Paths.get("/Users/raymanan11/Desktop/CSULB/CECS406/HF/test-network/organizations/peerOrganizations/org1.example.com/connection-org1.yaml");
//
//			builder = Gateway.createBuilder();
//			builder.identity(wallet, "appUser").networkConfig(networkConfigPath).discovery(true);
//			
//		}
//		catch (Exception e) {
//			System.out.println("Error: " + e);
//		}
//	}
		
//		public String getAllAirlineParts(Network network) {
//			try {
//				contract = network.getContract("fabcar");
//				// create a gateway connection
//				byte[] result;
//
//				result = contract.evaluateTransaction("QueryAllAirlineParts");
//				return new String(result);
//					
//			}
//			catch (Exception e) {
//				System.out.println("Error mania: " + e);
//				return "Error";
//			}
//		}
//		
//		public String queryAirlinePart(String airlinePart) {
//			try {
//
//				byte[] result;
//
//				result = contract.evaluateTransaction("QueryAirlinePart", airlinePart);
//				return new String(result);
//					
//			}
//			catch (Exception e) {
//				System.out.println("Error mania: " + e);
//				return "Error";
//			}
//		}
//		
//		public String changePartOwner(String airlinePart, String newOwner) {
//			try {
//
//				byte[] result;
//				//result = contract.evaluateTransaction("ChangeAirlinePartOwner", airlinePart, newOwner);
//				contract.evaluateTransaction("CreateAirlinePart", "PART10", "Windows", "true", "345WSD34", newOwner);
//				//System.out.println(queryAirlinePart("PART10"));
//				return new String("");
//					
//			}
//			catch (Exception e) {
//				System.out.println("Error mania: " + e);
//				return "Error";
//			}
//		}

	
	public String getAllAirlineParts() {
		try {
			Path walletPath = Paths.get("/Users/raymanan11/Desktop/CSULB/CECS406/HF/fabcar/HFRest/wallet");
			wallet = Wallets.newFileSystemWallet(walletPath);
			// load a CCP
			Path networkConfigPath = Paths.get("/Users/raymanan11/Desktop/CSULB/CECS406/HF/test-network/organizations/peerOrganizations/org1.example.com/connection-org1.yaml");

			Gateway.Builder builder = Gateway.createBuilder();
			builder.identity(wallet, "appUser").networkConfig(networkConfigPath).discovery(true);
			// create a gateway connection
			try (Gateway gateway = builder.connect()) {

				// get the network and contract
				Network network = gateway.getNetwork("mychannel");
				Contract contract = network.getContract("fabcar");
				
				byte[] result;
				
				result = contract.submitTransaction("QueryAllAirlineParts");

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

			Path walletPath = Paths.get("/Users/raymanan11/Desktop/CSULB/CECS406/HF/fabcar/HFRest/wallet");
			wallet = Wallets.newFileSystemWallet(walletPath);
			// load a CCP
			Path networkConfigPath = Paths.get("/Users/raymanan11/Desktop/CSULB/CECS406/HF/test-network/organizations/peerOrganizations/org1.example.com/connection-org1.yaml");

			Gateway.Builder builder = Gateway.createBuilder();
			builder.identity(wallet, "appUser").networkConfig(networkConfigPath).discovery(true);
			// create a gateway connection
			try (Gateway gateway = builder.connect()) {

				// get the network and contract
				Network network = gateway.getNetwork("mychannel");
				Contract contract = network.getContract("fabcar");
				
				byte[] result;
				
				result = contract.submitTransaction("QueryAirlinePart", airlinePart);

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

			Path walletPath = Paths.get("/Users/raymanan11/Desktop/CSULB/CECS406/HF/fabcar/HFRest/wallet");
			wallet = Wallets.newFileSystemWallet(walletPath);
			// load a CCP
			Path networkConfigPath = Paths.get("/Users/raymanan11/Desktop/CSULB/CECS406/HF/test-network/organizations/peerOrganizations/org1.example.com/connection-org1.yaml");

			Gateway.Builder builder = Gateway.createBuilder();
			builder.identity(wallet, "appUser").networkConfig(networkConfigPath).discovery(true);
			// create a gateway connection
			try (Gateway gateway = builder.connect()) {

				// get the network and contract
				Network network = gateway.getNetwork("mychannel");
				Contract contract = network.getContract("fabcar");
				
				byte[] result;

				result = contract.submitTransaction("ChangeAirlinePartOwner", airlinePart, newOwner);

				return new String(result);
			}
				
		}
		catch (Exception e) {
			System.out.println("Error mania: " + e);
			return "Error";
		}
	}
	
	
}
