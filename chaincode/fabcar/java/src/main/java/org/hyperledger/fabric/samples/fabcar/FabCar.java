/*
 * SPDX-License-Identifier: Apache-2.0
 */

package org.hyperledger.fabric.samples.fabcar;

import java.util.ArrayList;
import java.util.List;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contact;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Default;
import org.hyperledger.fabric.contract.annotation.Info;
import org.hyperledger.fabric.contract.annotation.License;
import org.hyperledger.fabric.contract.annotation.Transaction;
import org.hyperledger.fabric.shim.ChaincodeException;
import org.hyperledger.fabric.shim.ChaincodeStub;
import org.hyperledger.fabric.shim.ledger.KeyValue;
import org.hyperledger.fabric.shim.ledger.QueryResultsIterator;

import com.owlike.genson.Genson;

/**
 * Java implementation of the Fabric Car Contract described in the Writing Your
 * First Application tutorial
 */
@Contract(
        name = "FabCar",
        info = @Info(
                title = "FabCar contract",
                description = "The hyperlegendary car contract",
                version = "0.0.1-SNAPSHOT",
                license = @License(
                        name = "Apache 2.0 License",
                        url = "http://www.apache.org/licenses/LICENSE-2.0.html"),
                contact = @Contact(
                        email = "f.carr@example.com",
                        name = "F Carr",
                        url = "https://hyperledger.example.com")))
@Default
public final class FabCar implements ContractInterface {

    private final Genson genson = new Genson();

    private enum FabCarErrors {
        CAR_NOT_FOUND,
        CAR_ALREADY_EXISTS
    }

    /**
     * Retrieves a car with the specified key from the ledger.
     *
     * @param ctx the transaction context
     * @param key the key
     * @return the Car found on the ledger if there was one
     */
//    @Transaction()
//    public Car queryCar(final Context ctx, final String key) {
//        ChaincodeStub stub = ctx.getStub();
//        String carState = stub.getStringState(key);
//
//        if (carState.isEmpty()) {
//            String errorMessage = String.format("Car %s does not exist", key);
//            System.out.println(errorMessage);
//            throw new ChaincodeException(errorMessage, FabCarErrors.CAR_NOT_FOUND.toString());
//        }
//
//        Car car = genson.deserialize(carState, Car.class);
//
//        return car;
//    }

    /**
     * Creates some initial Cars on the ledger.
     *
     * @param ctx the transaction context
     */
    @Transaction()
    public void initLedger(final Context ctx) {
        ChaincodeStub stub = ctx.getStub();

        String[] carData = {
                "{ \"name\": \"Toyota\", \"isDefect\": \"true\", \"serialNumber\": \"blue\", \"owner\": \"Tomoko\" }",
                "{ \"name\": \"Ford\", \"isDefect\": \"false\", \"serialNumber\": \"red\", \"owner\": \"Brad\" }",
                "{ \"name\": \"Hyundai\", \"isDefect\": \"false\", \"serialNumber\": \"green\", \"owner\": \"Jin Soo\" }",
                "{ \"name\": \"Volkswagen\", \"isDefect\": \"true\", \"serialNumber\": \"yellow\", \"owner\": \"Max\" }",
                "{ \"name\": \"Tesla\", \"isDefect\": \"fa;se\", \"serialNumber\": \"black\", \"owner\": \"Adrian\" }",
                "{ \"name\": \"Peugeot\", \"isDefect\": \"true\", \"serialNumber\": \"purple\", \"owner\": \"Michel\" }",
                "{ \"name\": \"Chery\", \"isDefect\": \"true\", \"serialNumber\": \"white\", \"owner\": \"Aarav\" }",
                "{ \"name\": \"Fiat\", \"isDefect\": \"true\", \"serialNumber\": \"violet\", \"owner\": \"Pari\" }",
                "{ \"name\": \"Tata\", \"isDefect\": \"false\", \"serialNumber\": \"indigo\", \"owner\": \"Valeria\" }",
                "{ \"name\": \"Holden\", \"isDefect\": \"true\", \"serialNumber\": \"brown\", \"owner\": \"Shotaro\" }"
        };

        for (int i = 0; i < carData.length; i++) {
            String key = String.format("CAR%d", i);

            AirlinePart car = genson.deserialize(carData[i], AirlinePart.class);
            String carState = genson.serialize(car);
            stub.putStringState(key, carState);
        }
    }

    /**
     * Creates a new car on the ledger.
     *
     * @param ctx the transaction context
     * @param key the key for the new car
     * @param make the make of the new car
     * @param model the model of the new car
     * @param color the color of the new car
     * @param owner the owner of the new car
     * @return the created Car
     */
//    @Transaction()
//    public Car createCar(final Context ctx, final String key, final String make, final String model,
//            final String color, final String owner) {
//        ChaincodeStub stub = ctx.getStub();
//
//        String carState = stub.getStringState(key);
//        if (!carState.isEmpty()) {
//            String errorMessage = String.format("Car %s already exists", key);
//            System.out.println(errorMessage);
//            throw new ChaincodeException(errorMessage, FabCarErrors.CAR_ALREADY_EXISTS.toString());
//        }
//
//        Car car = new Car(make, model, color, owner);
//        carState = genson.serialize(car);
//        stub.putStringState(key, carState);
//
//        return car;
//    }

    /**
     * Retrieves all cars from the ledger.
     *
     * @param ctx the transaction context
     * @return array of Cars found on the ledger
     */
    @Transaction()
    public String QueryAllAirlineParts(final Context ctx) {
        ChaincodeStub stub = ctx.getStub();

        final String startKey = "CAR1";
        final String endKey = "CAR99";
        List<CarQueryResult> queryResults = new ArrayList<CarQueryResult>();

        QueryResultsIterator<KeyValue> results = stub.getStateByRange(startKey, endKey);

        for (KeyValue result: results) {
            AirlinePart car = genson.deserialize(result.getStringValue(), AirlinePart.class);
            queryResults.add(new CarQueryResult(result.getKey(), car));
        }

        final String response = genson.serialize(queryResults);

        return response;
    }

    /**
     * Changes the owner of a car on the ledger.
     *
     * @param ctx the transaction context
     * @param key the key
     * @param newOwner the new owner
     * @return the updated Car
     */
   @Transaction()
   public Car changeCarOwner(final Context ctx, final String key, final String newOwner) {
       ChaincodeStub stub = ctx.getStub();

       String carState = stub.getStringState(key);

       if (carState.isEmpty()) {
           String errorMessage = String.format("Car %s does not exist", key);
           System.out.println(errorMessage);
           throw new ChaincodeException(errorMessage, FabCarErrors.CAR_NOT_FOUND.toString());
       }

       Car car = genson.deserialize(carState, Car.class);

       Car newCar = new Car(car.getMake(), car.getModel(), car.getColor(), newOwner);
       String newCarState = genson.serialize(newCar);
       stub.putStringState(key, newCarState);

       return newCar;
   }
}
