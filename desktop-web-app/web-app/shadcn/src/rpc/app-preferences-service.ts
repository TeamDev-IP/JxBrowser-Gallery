import {createGrpcWebTransport} from "@connectrpc/connect-web";
import {createCallbackClient} from "@connectrpc/connect";
import {Account, ProfilePicture} from "@/gen/account_pb.ts";
import {AppPreferencesService} from "@/gen/app_preferences_pb.ts";
import {General} from "@/gen/general_pb.ts";

// A port for RPC communication passed obtained from the server side via
// the JxBrowser Java-Js bridge.
declare const rpcPort: Number

const transport = createGrpcWebTransport({
    baseUrl: `http://localhost:${rpcPort}`,
});
const appPreferencesClient = createCallbackClient(AppPreferencesService, transport);

function getAccount(callback: (account: Account) => void) {
    appPreferencesClient.getAccount({}, (_err, res) => {
        callback(res)
    });
}

function setAccount(newAccount: Account, callback?: (added: boolean) => void) {
    appPreferencesClient.setAccount(newAccount, (_err, res) => {
        callback && callback(res.value);
    });
}

function setProfilePicture(newProfilePicture: ProfilePicture, callback?: () => void) {
    appPreferencesClient.setProfilePicture(newProfilePicture, (_err) => {
        callback && callback();
    });
}

function getProfilePicture(callback?: (src: Uint8Array) => void) {
    appPreferencesClient.getProfilePicture({}, (_err, res) => {
        callback && callback(res.value);
    });
}

function setGeneral(newGeneralPrefs: General, callback?: () => void) {
    appPreferencesClient.setGeneral(newGeneralPrefs, (_err) => {
        callback && callback();
    });
}

function getGeneral(callback?: (generalPrefs: General) => void) {
    appPreferencesClient.getGeneral({}, (_err, res) => {
        callback && callback(res);
    });
}

export {
    getAccount,
    setAccount,
    setProfilePicture,
    getProfilePicture,
    setGeneral,
    getGeneral
}
