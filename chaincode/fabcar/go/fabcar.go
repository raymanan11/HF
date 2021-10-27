/*
SPDX-License-Identifier: Apache-2.0
*/

package main

import (
	"encoding/json"
	"fmt"
	"strconv"

	"github.com/hyperledger/fabric-contract-api-go/contractapi"
)

// SmartContract provides functions for managing a airline part
type SmartContract struct {
	contractapi.Contract
}

// Car describes basic details of what makes up a airline part
type AirlinePart struct {
	Name   string `json:"name"`
	IsDefect  bool `json:"isDefect"`
	SerialNumber string `json:"serialNumber"`
	Owner  string `json:"owner"`
}

// QueryResult structure used for handling result of query
type QueryResult struct {
	Key    string `json:"Key"`
	Record *AirlinePart
}

// InitLedger adds a base set of airline parts to the ledger
func (s *SmartContract) InitLedger(ctx contractapi.TransactionContextInterface) error {
	airlineParts := []AirlinePart{
		AirlinePart{Name: "Flight Controls", IsDefect: false, SerialNumber: "E96GJE93D", Owner: "United Airlines"},
		AirlinePart{Name: "Landing Gear", IsDefect: false, SerialNumber: "U46834HJ3", Owner: "American Airlines"},
		AirlinePart{Name: "Fuselage", IsDefect: true, SerialNumber: "FOIE463U2", Owner: "Delta"},
		AirlinePart{Name: "Rudder Pedals", IsDefect: false, SerialNumber: "DFU9436OB", Owner: "Spirit"},
		AirlinePart{Name: "Instrument Panels", IsDefect: false, SerialNumber: "FJE582KFD3", Owner: "Frontier"},
		AirlinePart{Name: "Engine", IsDefect: true, SerialNumber: "DFJRO895D", Owner: "Alaska Airlines"},
		AirlinePart{Name: "Wings", IsDefect: false, SerialNumber: "RID5569D2", Owner: "Southwest Airlines"},
		AirlinePart{Name: "Rudders", IsDefect: false, SerialNumber: "TOIE835D3", Owner: "JetBlue"},
		AirlinePart{Name: "Vertical Stabalizer", IsDefect: false, SerialNumber: "TI45GMD32W", Owner: "Hawaiian Airlines"},
		AirlinePart{Name: "Overhead Panel", IsDefect: true, SerialNumber: "EKLF8534H", Owner: "Allegiant Air"},
	}

	for i, parts := range airlineParts {
		partsAsBytes, _ := json.Marshal(parts)
		err := ctx.GetStub().PutState("PART"+strconv.Itoa(i), partsAsBytes)

		if err != nil {
			return fmt.Errorf("Failed to put to world state. %s", err.Error())
		}
	}

	return nil
}

// CreateAirlinePart adds a new airline part to the world state with given details
func (s *SmartContract) CreateAirlinePart(ctx contractapi.TransactionContextInterface, airlinePartNumber string, name string, isDefect bool, serialNumber string, owner string) error {
	airlinePart := AirlinePart{
		Name:   name,
		IsDefect:  isDefect,
		SerialNumber: serialNumber,
		Owner:  owner,
	}

	airlinePartAsBytes, _ := json.Marshal(airlinePart)

	return ctx.GetStub().PutState(airlinePartNumber, airlinePartAsBytes)
}

// QueryAirlinePart returns the airline part stored in the world state with given id
func (s *SmartContract) QueryAirlinePart(ctx contractapi.TransactionContextInterface, airlinePartNumber string) (*AirlinePart, error) {
	airlinePartAsBytes, err := ctx.GetStub().GetState(airlinePartNumber)

	if err != nil {
		return nil, fmt.Errorf("Failed to read from world state. %s", err.Error())
	}

	if airlinePartAsBytes == nil {
		return nil, fmt.Errorf("%s does not exist", airlinePartNumber)
	}

	airlinePart := new(AirlinePart)
	_ = json.Unmarshal(airlinePartAsBytes, airlinePart)

	return airlinePart, nil
}

// QueryAllAirlineParts returns all airline parts found in world state
func (s *SmartContract) QueryAllAirlineParts(ctx contractapi.TransactionContextInterface) ([]QueryResult, error) {
	startKey := ""
	endKey := ""

	resultsIterator, err := ctx.GetStub().GetStateByRange(startKey, endKey)

	if err != nil {
		return nil, err
	}
	defer resultsIterator.Close()

	results := []QueryResult{}

	for resultsIterator.HasNext() {
		queryResponse, err := resultsIterator.Next()

		if err != nil {
			return nil, err
		}

		airlinePart := new(AirlinePart)
		_ = json.Unmarshal(queryResponse.Value, airlinePart)

		queryResult := QueryResult{Key: queryResponse.Key, Record: airlinePart}
		results = append(results, queryResult)
	}

	return results, nil
}

// ChangeAirlinePartOwner updates the owner field of airline part with given id in world state
func (s *SmartContract) ChangeAirlinePartOwner(ctx contractapi.TransactionContextInterface, airlinePartNumber string, newOwner string) error {
	airlinePart, err := s.QueryAirlinePart(ctx, airlinePartNumber)

	if err != nil {
		return err
	}

	airlinePart.Owner = newOwner

	airlinePartAsBytes, _ := json.Marshal(airlinePart)

	return ctx.GetStub().PutState(airlinePartNumber, airlinePartAsBytes)
}

func main() {

	chaincode, err := contractapi.NewChaincode(new(SmartContract))

	if err != nil {
		fmt.Printf("Error create fabcar chaincode: %s", err.Error())
		return
	}

	if err := chaincode.Start(); err != nil {
		fmt.Printf("Error starting fabcar chaincode: %s", err.Error())
	}
}
