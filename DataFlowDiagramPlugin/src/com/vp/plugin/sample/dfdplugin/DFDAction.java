package com.vp.plugin.sample.dfdplugin;

import java.awt.Point;

import com.vp.plugin.ApplicationManager;
import com.vp.plugin.DiagramManager;
import com.vp.plugin.action.VPAction;
import com.vp.plugin.action.VPActionController;
import com.vp.plugin.diagram.IDiagramTypeConstants;
import com.vp.plugin.diagram.IDiagramUIModel;
import com.vp.plugin.diagram.connector.IDFDataFlowUIModel;
import com.vp.plugin.diagram.shape.IDFDataStoreUIModel;
import com.vp.plugin.diagram.shape.IDFExternalEntityUIModel;
import com.vp.plugin.diagram.shape.IDFProcessUIModel;
import com.vp.plugin.model.IDFDataFlow;
import com.vp.plugin.model.IDFDataStore;
import com.vp.plugin.model.IDFExternalEntity;
import com.vp.plugin.model.IDFProcess;
import com.vp.plugin.model.factory.IModelElementFactory;

public class DFDAction implements VPActionController {
	
	@Override
	public void performAction(VPAction arg0){
		//Create Blank DFD
		DiagramManager diagrammanager = ApplicationManager.instance().getDiagramManager();
		IDiagramUIModel dfd = (IDiagramUIModel) diagrammanager.createDiagram(IDiagramTypeConstants.DIAGRAM_TYPE_DATA_FLOW_DIAGRAM);
		dfd.setName("Simple Data Flow Diagram");
		
		//Create Order Food Process
		IDFProcess orderFood = IModelElementFactory.instance().createDFProcess();
		orderFood.setName("Order Food");
		//Create the process shape on diagram
		orderFood.setDfId("0.1");
		IDFProcessUIModel shapeOrderFood = (IDFProcessUIModel) diagrammanager.createDiagramElement(dfd, orderFood);
		shapeOrderFood.setBounds(450, 395, 100, 60);
		
		IDFProcess generateReports = IModelElementFactory.instance().createDFProcess();
		generateReports.setName("Generate Reports");
		generateReports.setDfId("0.2");
		IDFProcessUIModel shapeGenerateReports = (IDFProcessUIModel) diagrammanager.createDiagramElement(dfd, generateReports);
		shapeGenerateReports.setBounds(450, 595, 100, 60);
		
		IDFProcess orderInventory = IModelElementFactory.instance().createDFProcess();
		orderInventory.setName("Order Inventory");
		orderInventory.setDfId("0.3");
		IDFProcessUIModel shapeOrderInventory = (IDFProcessUIModel) diagrammanager.createDiagramElement(dfd, orderInventory);
		shapeOrderInventory.setBounds(450, 795, 100, 60);
		
		//Create External Entity
		IDFExternalEntity customer = IModelElementFactory.instance().createDFExternalEntity();
		customer.setName("Customer");
		//Create the external entity on diagram
		IDFExternalEntityUIModel shapeCustomer = (IDFExternalEntityUIModel) diagrammanager.createDiagramElement(dfd, customer);
		shapeCustomer.setBounds(450, 200, 100, 50);
		
		IDFExternalEntity kitchen = IModelElementFactory.instance().createDFExternalEntity();
		kitchen.setName("Kitchen");
		IDFExternalEntityUIModel shapeKitchen = (IDFExternalEntityUIModel) diagrammanager.createDiagramElement(dfd, kitchen);
		shapeKitchen.setBounds(700, 400, 100, 50);
		
		IDFExternalEntity manager = IModelElementFactory.instance().createDFExternalEntity();
		manager.setName("Manager");
		IDFExternalEntityUIModel shapeManager = (IDFExternalEntityUIModel) diagrammanager.createDiagramElement(dfd, manager);
		shapeManager.setBounds(700, 800, 100, 50);
		
		IDFExternalEntity supplier = IModelElementFactory.instance().createDFExternalEntity();
		supplier.setName("Supplier");
		IDFExternalEntityUIModel shapeSupplier = (IDFExternalEntityUIModel) diagrammanager.createDiagramElement(dfd, supplier);
		shapeSupplier .setBounds(200, 200, 100, 50);
		
		//Create Data Store
		IDFDataStore inventory = IModelElementFactory.instance().createDFDataStore();
		inventory.setName("inventory");
		//Create data store on diagram
		IDFDataStoreUIModel shapeInventory = (IDFDataStoreUIModel) diagrammanager.createDiagramElement(dfd, inventory);
		shapeInventory.setBounds(200, 400, 100, 50);
		
		IDFDataStore order = IModelElementFactory.instance().createDFDataStore();
		order.setName("Order");
		IDFDataStoreUIModel shapeOrder = (IDFDataStoreUIModel) diagrammanager.createDiagramElement(dfd, order);
		shapeOrder.setBounds(700, 600, 100, 50);
		
		//Create Data Flow
		IDFDataFlow customerOrderFood = IModelElementFactory.instance().createDFDataFlow();
		customerOrderFood.setName("Order");
		//The data flow is connecting from the customer external entity...
		customerOrderFood.setFrom(customer);
		//...to the order food process
		customerOrderFood.setTo(orderFood);
		//Create the data flow on diagram
		//If new Point[] is empty, the data flow will be created automatically 
		IDFDataFlowUIModel shapeCustomerOrderFood = 
				(IDFDataFlowUIModel) diagrammanager.createConnector(dfd, customerOrderFood, shapeCustomer, shapeOrderFood, new Point[] {new Point(480,250), new Point(480,395)});
		shapeCustomerOrderFood.resetCaption();
		
		IDFDataFlow customerGetBill = IModelElementFactory.instance().createDFDataFlow();
		customerGetBill.setName("Bill");
		customerGetBill.setFrom(orderFood);
		customerGetBill.setTo(customer);
		IDFDataFlowUIModel shapeCustomerGetBill = 
				(IDFDataFlowUIModel) diagrammanager.createConnector(dfd, customerGetBill, shapeOrderFood, shapeCustomer, new Point[] {new Point(520,395), new Point(520,250)});
		shapeCustomerGetBill.resetCaption();
		
		IDFDataFlow orderToKitchen = IModelElementFactory.instance().createDFDataFlow();
		orderToKitchen.setName("Order");
		orderToKitchen.setFrom(orderFood);
		orderToKitchen.setTo(kitchen);
		IDFDataFlowUIModel shapeOrderToKitchen = 
				(IDFDataFlowUIModel)diagrammanager.createConnector(dfd, orderToKitchen, shapeOrderFood, shapeKitchen, new Point[] {new Point(550,425), new Point(700,425)});		
		shapeOrderToKitchen.resetCaption();
		
		IDFDataFlow inventoryDetailToInventory = IModelElementFactory.instance().createDFDataFlow();
		inventoryDetailToInventory.setName("Inventory details");
		inventoryDetailToInventory.setFrom(orderFood);
		inventoryDetailToInventory.setTo(inventory);
		IDFDataFlowUIModel shapeInventoryDetailToInventory = 
				(IDFDataFlowUIModel) diagrammanager.createConnector(dfd, inventoryDetailToInventory, shapeOrderFood, shapeInventory, new Point[] {new Point(450,425), new Point(300,425)});
		shapeInventoryDetailToInventory.resetCaption();
		
		IDFDataFlow orderToOrder = IModelElementFactory.instance().createDFDataFlow();
		orderToOrder.setName("Order");
		orderToOrder.setFrom(orderFood);
		orderToOrder.setTo(order);
		IDFDataFlowUIModel shapeOrderToOrder = 
				(IDFDataFlowUIModel) diagrammanager.createConnector(dfd, orderToOrder, shapeOrderFood, shapeOrder, new Point[] {});
		shapeOrderToOrder.resetCaption();
		
		IDFDataFlow inventoryDetailToGenerateReport = IModelElementFactory.instance().createDFDataFlow();
		inventoryDetailToGenerateReport.setName("Inventory details");
		inventoryDetailToGenerateReport.setFrom(inventory);
		inventoryDetailToGenerateReport.setTo(generateReports);
		IDFDataFlowUIModel shapeInventoryDetailToGenerateReport = 
				(IDFDataFlowUIModel) diagrammanager.createConnector(dfd, inventoryDetailToGenerateReport, shapeInventory, shapeGenerateReports,new Point[] {});
		shapeInventoryDetailToGenerateReport.resetCaption();
		
		IDFDataFlow orderToGenerateReport = IModelElementFactory.instance().createDFDataFlow();
		orderToGenerateReport.setName("Order");
		orderToGenerateReport.setFrom(order);
		orderToGenerateReport.setTo(generateReports);
		IDFDataFlowUIModel shapeOrderToGenerateReport = 
				(IDFDataFlowUIModel) diagrammanager.createConnector(dfd, orderToGenerateReport, shapeOrder, shapeGenerateReports, new Point[] {new Point(700,625), new Point(550,625)});
		shapeOrderToGenerateReport.resetCaption();
		
		IDFDataFlow reportToManager = IModelElementFactory.instance().createDFDataFlow();
		reportToManager.setName("Reports");
		reportToManager.setFrom(generateReports);
		reportToManager.setTo(manager);
		IDFDataFlowUIModel shapeReportToManager = 
				(IDFDataFlowUIModel) diagrammanager.createConnector(dfd, reportToManager, shapeGenerateReports, shapeManager, null);
		shapeReportToManager.resetCaption();
		
		IDFDataFlow inventoryOrderToOrderInventory = IModelElementFactory.instance().createDFDataFlow();
		inventoryOrderToOrderInventory.setName("Inventory order");
		inventoryOrderToOrderInventory.setFrom(manager);
		inventoryOrderToOrderInventory.setTo(orderInventory);
		IDFDataFlowUIModel shapeInventoryOrderToOrderInventory = 
				(IDFDataFlowUIModel) diagrammanager.createConnector(dfd, inventoryOrderToOrderInventory, shapeManager, shapeOrderInventory, new Point[] {new Point(700,825), new Point(550,825)});
		shapeInventoryOrderToOrderInventory.resetCaption();
		
		IDFDataFlow inventoryOrderToSupplier = IModelElementFactory.instance().createDFDataFlow();
		inventoryOrderToSupplier.setName("Inventory order");
		inventoryOrderToSupplier.setFrom(orderInventory);
		inventoryOrderToSupplier.setTo(supplier);
		IDFDataFlowUIModel shapeInventoryOrderToSupplier = 
				(IDFDataFlowUIModel) diagrammanager.createConnector(dfd, inventoryOrderToSupplier, shapeInventory, shapeSupplier, null);
		shapeInventoryOrderToSupplier.resetCaption();
		
		IDFDataFlow orderInventoryToInventory = IModelElementFactory.instance().createDFDataFlow();
		orderInventoryToInventory.setName("inventory details");
		orderInventoryToInventory.setFrom(orderInventory);
		orderInventoryToInventory.setTo(inventory);
		IDFDataFlowUIModel shapeOrderInventoryToInventory = 
				(IDFDataFlowUIModel) diagrammanager.createConnector(dfd, orderInventoryToInventory, shapeOrderInventory, shapeInventory, new Point[] {new Point(450,825), new Point (250,825), new Point(250,450)});
		shapeOrderInventoryToInventory.resetCaption();
		
		// Show up the diagram
		diagrammanager.openDiagram(dfd);
	}
	
	@Override
	public void update(VPAction arg0) {
		// TODO Auto-generated method stub
		
	}
}
