package model;

import lombok.Getter;
import lombok.Setter;

import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;

@Setter
@Getter
public class Dominio {
	private org.eclipse.uml2.uml.Class classDom;
	private EList<Property> atributos;
	private EList<Stereotype> esteriotipos;
}
