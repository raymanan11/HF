/*
SPDX-License-Identifier: Apache-2.0
*/

package org.example;

import java.nio.file.Path;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
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
				
				result = contract.submitTransaction("ReadAsset", airlinePart);

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

				result = contract.submitTransaction("TransferAsset", airlinePart, newOwner);
				
				String uri = "amqp://localhost:5673";

				ConnectionFactory factory = new ConnectionFactory();
				factory.setUri(uri);

				  //Recommended settings
				factory.setConnectionTimeout(30000);

				Connection connection = factory.newConnection();
				Channel channel = connection.createChannel();

				String queue = "Hello";     //queue name
				boolean durable = false;    //durable - RabbitMQ will never lose the queue if a crash occurs
				boolean exclusive = false;  //exclusive - if queue only will be used by one connection
				boolean autoDelete = false; //autodelete - queue is deleted when last consumer unsubscribes

				channel.queueDeclare(queue, durable, exclusive, autoDelete, null);
				String message = new String(result);

				String exchangeName = "";
				String routingKey = "HF_ML";
				channel.basicPublish(exchangeName, routingKey, null, message.getBytes());
				System.out.println("Sent: '" + message + "'");

				return new String(result);
					
			}
				
		}
		catch (Exception e) {
			System.out.println("Error mania: " + e);
			return "Error";
		}
	}
	
	public String createPart(String airlinePart, String partName, String newOwner, String quantity) {
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

				result = contract.submitTransaction("CreateAsset", airlinePart, partName, quantity, newOwner);
				
				String uri = "amqp://localhost:5673";

				ConnectionFactory factory = new ConnectionFactory();
				factory.setUri(uri);

				  //Recommended settings
				factory.setConnectionTimeout(30000);

				Connection connection = factory.newConnection();
				Channel channel = connection.createChannel();

				String queue = "Hello";     //queue name
				boolean durable = false;    //durable - RabbitMQ will never lose the queue if a crash occurs
				boolean exclusive = false;  //exclusive - if queue only will be used by one connection
				boolean autoDelete = false; //autodelete - queue is deleted when last consumer unsubscribes

				channel.queueDeclare(queue, durable, exclusive, autoDelete, null);
				String message = new String(result);

				String exchangeName = "";
				String routingKey = "HF_ML";
				channel.basicPublish(exchangeName, routingKey, null, message.getBytes());
				System.out.println("Sent: '" + message + "'");

				return new String(result);
			}
				
		}
		catch (Exception e) {
			System.out.println("Error mania: " + e.getMessage());
			return "Error";
		}
	}
	
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
				
				result = contract.submitTransaction("QueryAllParts");

				return new String(result);
			}
				
		}
		catch (Exception e) {
			System.out.println("Error mania: " + e);
			return "Error";
		}
	}
	
	
}
