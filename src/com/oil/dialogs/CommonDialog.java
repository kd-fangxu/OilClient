package com.oil.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

public class CommonDialog {
	Context context;
	Dialog dialog;
	int layId;
	IfaceCommonDialogInit ifaCommonDialogInit;

	public CommonDialog(Context context, int layId) {
		this.context = context;
		this.layId = layId;

	};

	public CommonDialog(Context context, int layId,
			IfaceCommonDialogInit ifaCommonDialogInit) {
		this.context = context;

	}

	public IfaceCommonDialogInit getIfaCommonDialogInit() {
		return ifaCommonDialogInit;
	}

	public void setIfaCommonDialogInit(IfaceCommonDialogInit ifaCommonDialogInit) {
		this.ifaCommonDialogInit = ifaCommonDialogInit;
		View view = View.inflate(context, layId, null);
		dialog = new Dialog(context);
		dialog.setContentView(view);
		ifaCommonDialogInit.dialogInit(view);
	};

}
