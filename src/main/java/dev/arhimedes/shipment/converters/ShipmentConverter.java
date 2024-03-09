package dev.arhimedes.shipment.converters;

import dev.arhimedes.global.service.contract.Converter;
import dev.arhimedes.global.service.contract.EncryptionService;
import dev.arhimedes.product.repository.ProductRepository;
import dev.arhimedes.shipment.dtos.ShipmentDTO;
import dev.arhimedes.shipment.entity.Shipment;
import dev.arhimedes.shipment.repository.ShipmentAddressRepository;
import dev.arhimedes.shipment.repository.ShipmentRepository;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("ShipmentConverter")
@RequiredArgsConstructor
public class ShipmentConverter implements Converter<Shipment, ShipmentDTO> {

    private final EncryptionService encryptionService;

    private final ShipmentRepository shipmentRepository;

    private final ShipmentAddressRepository shipmentAddressRepository;

    private final ProductRepository productRepository;

    @Override
    public ShipmentDTO convert(Shipment shipment, ShipmentDTO shipmentDTO) {

        if(null == shipmentDTO){
            shipmentDTO = new ShipmentDTO();
        }

        if(0 != shipment.getShipmentId()){
            shipmentDTO.setShipmentId(
                    encryptionService.encrypt(String.valueOf(shipment.getShipmentId()))
            );
        }

        if(null != shipment.getOrigin()){
            shipmentDTO.setOriginAddressId(
                    encryptionService.encrypt(String.valueOf(shipment.getOrigin().getId()))
            );

            shipmentDTO.setOriginCity(shipment.getOrigin().getCity());
        }


        if(null != shipment.getDestination()){
            shipmentDTO.setDestinationAddressId(
                    encryptionService.encrypt(String.valueOf(shipment.getDestination().getId()))
            );

            shipmentDTO.setDestinationCity(shipment.getDestination().getCity());
        }

        if(null != shipment.getProduct()){
            shipmentDTO.setProductId(
                    encryptionService.encrypt(String.valueOf(shipment.getProduct().getProductId()))
            );
        }

        shipmentDTO.setDispatchDate(shipment.getDispatchDate());

        shipmentDTO.setDeliveredTime(shipment.getDeliveredTime());

        return shipmentDTO;
    }

    @Override
    public Shipment reverseConvert(ShipmentDTO shipmentDTO, Shipment shipment) {

        if(null == shipment){
            shipment = new Shipment();
        }

        if(StringUtils.isNotBlank(shipmentDTO.getShipmentId())){
            shipment.setShipmentId(
                    Integer.parseInt(encryptionService.decrypt(shipmentDTO.getShipmentId()))
            );
        }

        if(StringUtils.isNotBlank(shipmentDTO.getDestinationAddressId())){

            int id = Integer.parseInt(encryptionService.decrypt(shipmentDTO.getOriginAddressId()));

            if(shipmentAddressRepository.existsById(id)){
                shipment.setOrigin(
                        shipmentAddressRepository.getReferenceById(id)
                );
            }
        }

        if(StringUtils.isNotBlank(shipmentDTO.getDestinationAddressId())){

            int id = Integer.parseInt(encryptionService.decrypt(shipmentDTO.getDestinationAddressId()));

            if(shipmentAddressRepository.existsById(id)){
                shipment.setDestination(
                        shipmentAddressRepository.getReferenceById(id)
                );
            }
        }

        if(StringUtils.isNotBlank(shipmentDTO.getProductId())){
            int id = Integer.parseInt(encryptionService.decrypt(shipmentDTO.getProductId()));

            if(productRepository.existsById(id)){
                shipment.setProduct(
                        productRepository.getReferenceById(id)
                );
            }
        }

        shipment.setDispatchDate(shipmentDTO.getDispatchDate());
        shipment.setDeliveredTime(shipmentDTO.getDeliveredTime());

        return shipment;
    }
}
