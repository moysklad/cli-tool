package com.lognex.api.cli.commands.assortment;

import com.lognex.api.LognexApi;
import com.lognex.api.cli.commands.ThrowingBiFunction;
import com.lognex.api.cli.commands.ThrowingConsumer;
import com.lognex.api.cli.events.ConnectionEvent;
import com.lognex.api.clients.ProductClient;
import com.lognex.api.clients.ProductFolderClient;
import com.lognex.api.clients.ServiceClient;
import com.lognex.api.entities.MetaEntity;
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
            performAll((offset, limit) -> productFolderClient.get(offset, limit), folder -> {
                folder.setArchived(true);
                productFolderClient.put(folder);
            });
            performAll((offset, limit) -> productClient.get(offset, limit), product -> {
                product.setArchived(true);
                productClient.put(product);
            });
            performAll((offset, limit) -> serviceClient.get(offset, limit), service -> {
                service.setArchived(true);
                serviceClient.put(service);
            });
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

            performAll((offset, limit) -> productFolderClient.get(offset, limit, filterExpressions), folder -> {
                folder.setArchived(false);
                productFolderClient.put(folder);
            });
            performAll((offset, limit) -> productClient.get(offset, limit, filterExpressions), product -> {
                product.setArchived(false);
                productClient.put(product);
            });
            performAll((offset, limit) -> serviceClient.get(offset, limit, filterExpressions), service -> {
                service.setArchived(false);
                serviceClient.put(service);
            });

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

            performAll(
                    (offset, limit) -> productFolderClient.get(offset, limit, filterExpressions),
                    folder -> productFolderClient.delete(folder.getId())
            );
            performAll(
                    (offset, limit) -> productClient.get(offset, limit, filterExpressions),
                    product -> productClient.delete(product.getId())
            );
            performAll(
                    (offset, limit) -> serviceClient.get(offset, limit, filterExpressions),
                    service -> serviceClient.delete(service.getId())
            );
            System.out.println("Deleting all assortment is finished");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private <T extends MetaEntity> void performAll(ThrowingBiFunction<Integer, Integer, ListEntity<T>, Exception> extract,
                                                   ThrowingConsumer<T, Exception> perform) throws Exception {
        final int limit = 100;
        int offset = 0;

        ListEntity<T> list;
        do {
            list = extract.accept(offset, limit);
            for (T entity : list.getRows()) {
                perform.accept(entity);
            }
            offset += limit;
        } while(list.getMeta().getSize() >= offset);

    }

    @EventListener
    public void connectionHandle(ConnectionEvent event) {
        if(event.getConnectionDetails() == null) {
            productFolderClient = null;
            productClient = null;
            serviceClient = null;
        } else {
            LognexApi api = event.getConnectionDetails().getApi();
            productFolderClient = new ProductFolderClient(api);
            productClient = new ProductClient(api);
            serviceClient = new ServiceClient(api);
        }

    }
}
