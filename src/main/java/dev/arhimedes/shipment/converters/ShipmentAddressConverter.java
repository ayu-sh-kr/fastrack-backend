package dev.arhimedes.shipment.converters;

import dev.arhimedes.global.service.contract.Converter;
import dev.arhimedes.global.service.contract.EncryptionService;
import dev.arhimedes.shipment.dtos.ShipmentAddressDTO;
import dev.arhimedes.shipment.entity.ShipmentAddress;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("ShipmentAddressConverter")
@RequiredArgsConstructor
public class ShipmentAddressConverter implements Converter<ShipmentAddress, ShipmentAddressDTO> {

    private final EncryptionService encryptionService;

    @Override
    public ShipmentAddressDTO convert(ShipmentAddress shipmentAddress, ShipmentAddressDTO shipmentAddressDTO) {

        if(null == shipmentAddressDTO){
            shipmentAddressDTO = new ShipmentAddressDTO();
        }

        if(0 != shipmentAddress.getId()){
            shipmentAddressDTO.setId(
                    encryptionService.encrypt(String.valueOf(shipmentAddress.getId()))
            );
        }

        shipmentAddressDTO.setAddressLine1(shipmentAddress.getAddressLine1());
        shipmentAddressDTO.setAddressLine2(shipmentAddress.getAddressLine2());
        shipmentAddressDTO.setCity(shipmentAddress.getCity());
        shipmentAddressDTO.setZipcode(shipmentAddress.getZipcode());
        shipmentAddressDTO.setState(shipmentAddress.getState());

        return shipmentAddressDTO;
    }

    @Override
    public ShipmentAddress reverseConvert(ShipmentAddressDTO shipmentAddressDTO, ShipmentAddress shipmentAddress) {

        if(null == shipmentAddress){
            shipmentAddress = new ShipmentAddress();
        }

        if(StringUtils.isNotBlank(shipmentAddressDTO.getId())){
            shipmentAddress.setId(
                    Integer.parseInt(encryptionService.decrypt(shipmentAddressDTO.getId()))
            );
        }

        shipmentAddress.setAddressLine1(shipmentAddressDTO.getAddressLine1());
        shipmentAddress.setAddressLine2(shipmentAddressDTO.getAddressLine2());
        shipmentAddress.setCity(shipmentAddressDTO.getCity());
        shipmentAddress.setZipcode(shipmentAddressDTO.getZipcode());
        shipmentAddress.setState(shipmentAddressDTO.getState());

        return shipmentAddress;
    }
}
