package com.lognex.api.cli.commands.assortment;

import com.lognex.api.LognexApi;
import com.lognex.api.cli.events.ConnectionEvent;
import com.lognex.api.clients.ProductClient;
import com.lognex.api.clients.ProductFolderClient;
import com.lognex.api.clients.ServiceClient;
import com.lognex.api.entities.ProductFolderEntity;
import com.lognex.api.entities.products.ProductEntity;
import com.lognex.api.entities.products.ServiceEntity;
import com.lognex.api.responses.ListEntity;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AssortmentService {

    private ProductFolderClient productFolderClient;
    private ProductClient productClient;
    private ServiceClient serviceClient;


    public void archiveAssortment() {
        try {
            System.out.println("Archiving assortment is started");
            ListEntity<ProductFolderEntity> folders = productFolderClient.get();
            for (ProductFolderEntity folder : folders.getRows()) {
                folder.setArchived(true);
                productFolderClient.put(folder);
            }
            ListEntity<ProductEntity> products = productClient.get();
            for (ProductEntity product : products.getRows()) {
                product.setArchived(true);
                productClient.put(product);
            }
            ListEntity<ServiceEntity> services = serviceClient.get();
            for (ServiceEntity service : services.getRows()) {
                service.setArchived(true);
                serviceClient.put(service);
            }
            System.out.println("Archiving assortment is finished");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void unarchiveAssortment() {
        try {
            System.out.println("Unarchiving all assortment is started");
            List<String> filterExpressions = new ArrayList<>();
            filterExpressions.add("archived=true");

            ListEntity<ProductFolderEntity> folders = productFolderClient.get(filterExpressions);
            for (ProductFolderEntity folder : folders.getRows()) {
                folder.setArchived(false);
                productFolderClient.put(folder);
            }
            ListEntity<ProductEntity> products = productClient.get(filterExpressions);
            for (ProductEntity product : products.getRows()) {
                product.setArchived(false);
                productClient.put(product);
            }
            ListEntity<ServiceEntity> services = serviceClient.get(filterExpressions);
            for (ServiceEntity service : services.getRows()) {
                service.setArchived(false);
                serviceClient.put(service);
            }
            System.out.println("Unarchiving all assortment is finished");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void deleteAssortment() {
        try {
            System.out.println("Deleting all assortment is started");
            List<String> filterExpressions = new ArrayList<>();
            filterExpressions.add("archived=true");
            filterExpressions.add("archived=false");

            ListEntity<ProductFolderEntity> folders = productFolderClient.get(filterExpressions);
            for (ProductFolderEntity folder : folders.getRows()) {
                productFolderClient.delete(folder.getId());
            }
            ListEntity<ProductEntity> products = productClient.get(filterExpressions);
            for (ProductEntity product : products.getRows()) {
                productClient.delete(product.getId());
            }
            ListEntity<ServiceEntity> services = serviceClient.get(filterExpressions);
            for (ServiceEntity service : services.getRows()) {
                serviceClient.delete(service.getId());
            }
            System.out.println("Deleting all assortment is finished");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @EventListener
    public void connectionHandle(ConnectionEvent event) {
        LognexApi api = event.getConnectionDetails().getApi();
        productFolderClient = new ProductFolderClient(api);
        productClient = new ProductClient(api);
        serviceClient = new ServiceClient(api);
    }
}
