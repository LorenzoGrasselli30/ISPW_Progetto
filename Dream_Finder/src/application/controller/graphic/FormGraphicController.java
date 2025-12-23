package application.controller.graphic;

import application.model.bean.BookingContext;

public class FormGraphicController {
	private BookingContext context; // Lo stato corrente

    // Metodo chiamato dal controller precedente
    public void initForm(BookingContext context) {
        this.context = context;
        // Opzionale: usa context.getActivity().getName() per mostrare un titolo nella view
    }

}
