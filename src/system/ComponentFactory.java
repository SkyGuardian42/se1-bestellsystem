package system;

import datamodel.RawDataFactory;

public final class ComponentFactory {
    private static ComponentFactory instance;
    private final InventoryManager inventoryManager;
    private final OrderProcessor orderProcessor;
    private final OutputProcessor outputProcessor;
    private final DataFactory dataFactory;


    private ComponentFactory() {
        this.inventoryManager = new InventoryManager();
        this.orderProcessor = new OrderProcessor(inventoryManager);
        this.outputProcessor = new OutputProcessor(inventoryManager, orderProcessor);

        RawDataFactory.RawDataFactoryIntf objectRawFactory =
                RawDataFactory.getInstance( this );
        this.dataFactory = new DataFactory(objectRawFactory, inventoryManager, outputProcessor);
    }

    /**
     * Returns or creates a new instance of a ComponentFactory if needed
     * @return the ComponentFactory
     */
    public static ComponentFactory getInstance() {
        if(instance == null) {
            instance = new ComponentFactory();
        }
        return instance;
    }

    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    public OrderProcessor getOrderProcessor() {
        return orderProcessor;
    }

    public OutputProcessor getOutputProcessor() {
        return outputProcessor;
    }

    public DataFactory getDataFactory() {
        return dataFactory;
    }
}
