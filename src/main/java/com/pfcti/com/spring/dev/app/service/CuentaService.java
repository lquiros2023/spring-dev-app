package com.pfcti.com.spring.dev.app.service;

import com.pfcti.com.spring.dev.app.criteria.CuentaSpecification;
import com.pfcti.com.spring.dev.app.dto.ClienteDto;
import com.pfcti.com.spring.dev.app.dto.CuentaDto;
import com.pfcti.com.spring.dev.app.dto.NotificationDto;
import com.pfcti.com.spring.dev.app.jms.publishers.NotificationPubSubSender;
import com.pfcti.com.spring.dev.app.jms.senders.NotificationSender;
import com.pfcti.com.spring.dev.app.model.Cliente;
import com.pfcti.com.spring.dev.app.model.Cuenta;
import com.pfcti.com.spring.dev.app.repository.CuentaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.integration.support.MessageBuilder;

import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j

public class CuentaService {
    private CuentaRepository cuentaRepository;
    private CuentaSpecification cuentaSpecification;
    private NotificationSender notificationSender;
    private ClienteService clienteService;
    private NotificationPubSubSender notificationPubSubSender;

    private Cuenta fromDtoToCuenta(CuentaDto cuentaDto) {
        Cuenta cuenta = new Cuenta();
        BeanUtils.copyProperties(cuentaDto, cuenta);
        Cliente cliente = new Cliente();
        cliente.setId(cuentaDto.getClienteId());
        cuenta.setCliente(cliente);
        return cuenta;
    }

    private CuentaDto fromCuentaToCuentaDto(Cuenta cuenta) {
        CuentaDto cuentaDto = new CuentaDto();

        BeanUtils.copyProperties(cuenta, cuentaDto);
        return cuentaDto;
    }

    public List<CuentaDto> busquedaCuentasPorCriterio(CuentaDto cuentaDtoFilter) {
        return cuentaRepository
                .findAll(cuentaSpecification.buildFilter(cuentaDtoFilter))
                .stream()
                .map(this::fromCuentaToCuentaDto)
                .collect(Collectors.toList());
    }


    public void insertarCuenta(CuentaDto cuentaDto) {
        Cuenta cuenta = new Cuenta();
        Cliente cliente = new Cliente();
        cliente.setId(cuentaDto.getClienteId());
        cuenta.setCliente(cliente);
        cuenta.setEstado(cuentaDto.getEstado());
        cuenta.setTipo(cuentaDto.getTipo());
        cuenta.setNumero(cuentaDto.getNumero());
        cuentaRepository.save(cuenta);
    }

    public void actualizarCuenta(CuentaDto cuentaDto) {
        Cuenta cuenta = cuentaRepository.findById(cuentaDto.getId())
                .orElseThrow(() -> {
                            throw new RuntimeException("NO Existe el cliente");
                        }
                );
        cuenta.setId(cuentaDto.getId());
        cuenta.setEstado(cuentaDto.getEstado());

        cuentaRepository.save(cuenta);
    }

    public List<CuentaDto> BuscarCuentaCliente(int cliente_id) {
        List<CuentaDto> cuentaDtos = new ArrayList<>();
        List<Cuenta> cuentas = new ArrayList<>();
        cuentas = cuentaRepository.findCuentaByCliente_Id(cliente_id);
        cuentas.forEach(cuenta -> {
            cuentaDtos.add(fromCuentaToCuentaDto(cuenta));
        });


        return cuentaDtos;
    }

    public void sendNotification(CuentaDto cuentaDto) {
        ClienteDto clienteDto = clienteService.obtenerCliente(cuentaDto.getClienteId());
        NotificationDto notificacionDto = new NotificationDto();
        notificacionDto.setPhoneNumber(clienteDto.getTelefono());
        notificacionDto.setMailBody("Estimado " + clienteDto.getNombre() + " " + clienteDto.getApellidos() + " tu cuenta número " + cuentaDto.getNumero() + " se ha creado con éxito.");
        this.notificationSender.sendMail(notificacionDto);
    }

    private static String getMailBody(CuentaDto cuentaDto, ClienteDto clienteDto) {
        StringBuilder bodyBuilder = new StringBuilder();
        bodyBuilder.append("Estimado ");
        bodyBuilder.append(clienteDto.getNombre());
        bodyBuilder.append(" ");
        bodyBuilder.append(clienteDto.getApellidos());
        bodyBuilder.append(" tu cuenta número ");
        bodyBuilder.append(cuentaDto.getNumero());
        bodyBuilder.append(" se ha creado con éxito.");
        return bodyBuilder.toString();

    }

    public void creacionDeCuenta(CuentaDto cuentaDto){
        Cuenta cuenta = fromDtoToCuenta(cuentaDto);
        cuentaRepository.save(cuenta);
        log.info("Cuenta: {} ", cuenta);
    }

    public void creacionDeCuentaYNotificacion(CuentaDto cuentaDto) {
        creacionDeCuenta(cuentaDto);
        sendNotification(cuentaDto);
    }

    public void creacionDeCuentaPublishSub(CuentaDto cuentaDto) {
        creacionDeCuenta(cuentaDto);
        sendCreateAccountNotification(cuentaDto);
    }

    public void sendCreateAccountNotification(CuentaDto cuentaDto) {
        log.info("Empezando envío de notificaciones");
        Message<CuentaDto> message = MessageBuilder.withPayload(cuentaDto).build();
        Message<String> result = notificationPubSubSender.sendNotification(message);
        log.info("Resultado envío notificación: {}", result.getPayload());
    }
}
