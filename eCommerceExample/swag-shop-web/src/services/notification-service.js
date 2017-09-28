export const NOTIF_WISHLIST_CHANGED = "notif_wishlist_changed";

var observers = {};
let instance = null;

class NotificationService {

    constructor() {
        if (!instance) {
            instance = this;
        }

        return instance;
    }

    postNotification = (notificationName, data) => {
        let obs = observers[notificationName];
        for (var i = 0; i < obs.length; i++) {
            var obj = obs[i];
            obj.callBack(data);
        }
    }

    addObserver = (notificationName, observer, callBack) => {
        let obs = observers[notificationName];

        if (!obs) {
            observers[notificationName] = [];
        }

        let obj = {observer: observer, callBack: callBack};
        observers[notificationName].push(obj);
    }

    removeObserver = (observer, notificationName) => {
        let obs = observers[notificationName];

        if (obs) {
            for (var i = 0; i < obs.length; i++) {
                if (observer === obs[i].observer) {
                    obs.splice(i, 1);
                    observers[notificationName] = obs;
                    break;
                }
            }
        }
    }

}

export default NotificationService;