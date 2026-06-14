package com.Angelh0.stayhub.util;

public class EmailTemplates {

    public static String templateAccommodationDraft(String AccommodationName, String OwnerName) {
        return "<div style='font-family: sans-serif; max-width: 600px; padding: 20px; background-color: #000; color: #fff; border-radius: 15px;'>" +
                "<h2 style='color: #facc15;'>¡Borrador Creado en StayHub! 🏠</h2>" +
                "<p>Hola " + OwnerName + ",</p>" +
                "<p>Tu alojamiento <strong>" + AccommodationName + "</strong> se ha guardado correctamente en modo <strong>Draft</strong>.</p>" +
                "<p>Recuerda completar los pasos requeridos desde tu panel de propietario para poder publicarlo y empezar a recibir huéspedes.</p>" +
                "<hr style='border-color: #222;'>" +
                "<p style='font-size: 12px; color: #666;'>Equipo de soporte de StayHub.</p>" +
                "</div>";
    }

    public static String templateAccommodationCreated(String OwnerName, String AccommodationName) {
        return "<div style='font-family: sans-serif; max-width: 600px; padding: 20px; background-color: #000; color: #fff; border-radius: 15px;'>" +
                "<h2 style='color: #facc15;'>¡Borrador Creado en StayHub! 🏠</h2>" +
                "<p>Hola " + OwnerName + ",</p>" +
                "<p>Tu alojamiento <strong>" + AccommodationName + "</strong> se ha guardado correctamente en modo <strong>Draft</strong>.</p>" +
                "<p></p>" +
                "<hr style='border-color: #222;'>" +
                "<p style='font-size: 12px; color: #666;'>Equipo de soporte de StayHub.</p>" +
                "</div>";
    }
}
