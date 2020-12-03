package com.ffbb.resultats.db;

import java.net.URI;

import com.ffbb.resultats.api.Organisation;

public class OrganisationController extends Controller<Organisation, Boolean, Boolean> {

	protected OrganisationController() {
		super(new OrganisationReader(), new OrganisationUpdater());
	}

	@Override
	public Organisation doFind(URI uri) {
		return null;
	}

}
