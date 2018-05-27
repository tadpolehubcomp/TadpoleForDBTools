/*******************************************************************************
 * Copyright (c) 2013 hangum.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v2.1
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     hangum - initial API and implementation
 ******************************************************************************/
package com.hangum.tadpole.rdb.core.actions.connections;

import org.eclipse.jface.action.IAction;

import com.hangum.tadpole.engine.query.dao.system.UserDBDAO;
import com.hangum.tadpole.rdb.core.util.FindEditorAndWriteQueryUtil;
import com.hangum.tadpole.rdb.core.util.QueryTemplateUtils;
import com.tadpole.common.define.core.define.PublicTadpoleDefine;

/**
 * function 생성 action
 * 
 * @author hangum
 *
 */
public class CreateSynonymAction extends AbstractQueryAction {

	public CreateSynonymAction() {
		super();
	}

	@Override
	public void run(IAction action) {
		UserDBDAO userDB = (UserDBDAO)sel.getFirstElement();
		
		FindEditorAndWriteQueryUtil.run(userDB, 
					QueryTemplateUtils.getQuery(userDB, PublicTadpoleDefine.OBJECT_TYPE.SYNONYM), 
					PublicTadpoleDefine.OBJECT_TYPE.SYNONYM);
	}

}
