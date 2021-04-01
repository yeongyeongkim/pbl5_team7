package pt.up.fe.ssin.pexplorer.actions;

import pt.up.fe.ssin.pexplorer.R;
import pt.up.fe.ssin.pexplorer.app.PermissionAction;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class GetAccountsAction extends PermissionAction {

    public GetAccountsAction() {
        super(R.string.get_accounts_label, R.string.get_accounts_label,
                PermissionAction.DO_NOTHING);
    }

    @Override
    protected void doAction(final Context context) {
        Account[] accounts = AccountManager.get(context).getAccounts();
        String currentAccounts = context.getString(R.string.get_accounts_intro);
        for (Account account : accounts)
            currentAccounts += String.format(
                    context.getString(R.string.get_accounts_entry),
                    account.name, account.type);

        new AlertDialog.Builder(context)
                .setTitle(R.string.get_accounts_tile)
                .setMessage(currentAccounts)
                .setCancelable(true)
                .setPositiveButton(R.string.continue_,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        }).show();
    }
}
