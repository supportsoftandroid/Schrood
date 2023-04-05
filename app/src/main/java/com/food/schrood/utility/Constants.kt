package com.food.schrood.utility

object Constants {
    const val COUNTRY_NAME = "AU"
    const val COUNTRY_CODE = "+61"
    const val SHARED_PREFERENCE_FILE_NAME = "geno_education"
    const val DEVICE_TYPE = "android"
    const val STRIPE_PUBLISH_KEY = "pk_test_51LicFNFkV20vz5IvboMHk365cdTOHJ310eS4NgBJ2avgBTWLRgnMrQZVEEOTr9Y3s6YB7F6gtFkAhXSlx27rKd1S00e7dSexd3"


    /*  APP URLs    */
    const val LIVE_URL = "http://schrood.ssdemo.com.au/api/"
    const val FIREBASE_DB_URL = "https://schrood-99b12-default-rtdb.firebaseio.com/"

    //    const val FIREBASE_FCM_URL = "https://fcm.googleapis.com/fcm/send"
    const val FCM_PROJECT_ID = "schrood-99b12"
    const val FIREBASE_FCM_URL =
        "https://fcm.googleapis.com/v1/projects/$FCM_PROJECT_ID/messages:send"
    const val SERVER_KEY =
        "AAAAjIcWvbA:APA91bFPSikcFQ54IVoQxKCo_l-52dcK-WZ-t7D5OsgwItaYfROrOOSRb57t17C3AATWOH1j0BHq3TKiOpgSH7s9pMxHY9NuyBkMAViAfEGcEi96PotMR6HK74aaOJzQE5NuAU_-Y0qv"


    /*  API CALL CONSTANTS  */
    const val API_WELCOME_SCREEN = "welcomescreen"
    const val ACCEPT_JSON_HEADER = "Accept: application/json"
    const val FCM_JSON_HEADER = "Authorization: key=$SERVER_KEY"
    // const val ACCEPT_JSON_HEADER = "Content-Type: application/json"
    const val  PROFILE_EDIT_REQUEST_KEY="ptofile_edit_tutor"
    const val API_State_LIST = "statelist"
    const val API_CLASS_LIST = "classlist"
    const val API_SUBJECT_LIST = "subjectList"
    const val API_SUBJECT_BRANCH_LIST = "subjectbranchList"
    const val API_LOGIN = "login"
    const val API_SIGNUP = "register"
    const val API_FORGOT_PASS = "forgotpassword"
    const val API_VERIFY_OTP = "verify_otp"
    const val API_NEW_PASSWORD = "new_password"
    const val API_PROFILE_DETAILS = "profile_details"
    const val API_UPLOAD_PROFILE_IMAGE = "uploadimage"
    const val API_EDIT_PROFILE = "edit_profile"
    const val API_STUDENT_HOME = "nearbytutor"
    const val API_SEARCH_TUTOR = "searchtutorbyname"
    const val API_TUTOR_LIST = "subjecttutorList"
    const val API_SEARCH_FILTER = "searchfilter"
    const val API_TUTOR_DETAILS = "tutor_details"
    const val API_AVAILABLE_SLOT_LIST = "availableslot"
    const val API_BOOKING_NOW = "booknow"
    const val API_MY_BOOKING_LIST = "mybooking"
    const val API_REVENUE_LIST = "revenuebooking"
    const val API_BOOKING_Details = "booking_details"
    const val API_BOOKING_STATUS_UPDATE = "bookingstatus"
    const val API_CANCEL_BOOKING = "cancelbooking"
    const val API_ADD_CARD = "add_new_card"
    const val API_DELETE_CARD = "deletecard"
    const val API_SAVED_CARD = "savedcard"
    const val API_ADD_RATTING = "addrating"
    const val API_RATTING_LIST = "ratinglist"
    const val API_NOTIFICATION_LIST = "notificationslist"
    const val API_NOTIFICATION_READ = "readnotifications"
    const val API_NOTIFICATION_DELETE = "deletenotifications"
    const val API_TUTOR_HOME = "tutor_dashboard"
    const val API_TIME_SLOT_LIST = "timeslotlist"

    const val API_UPDATE_TIME_SLOT = "UpdateTimeslotTutor"
    const val API_ADD_BANK = "add_bank"
    const val API_BANK_LIST = "banklist"
    const val API_EDIT_BANK = "edit_bank"
    const val API_DELETE_BANK = "deletebank"
    const val API_CHANGE_PASSWORD = "change_password"


    const val API_CHAT_USER = "messages/chats"
    const val API_DELETE_CHAT_USER = "messages/delete-chat"

    const val API_POST_REVIEW = "post-review"
    const val API_USER_REVIEWS = "user-reviews"

    const val API_PRIVACY_POLICY = "privacypolicy"
    const val API_ABOUT_APP = "about_app"
    const val API_TERMS_CONDITION = "termsAndcondition"
    const val API_FAQ = "faq"
    const val API_HELP_CENTER = "help_center"

    /* IMPORTANT TEXTUAL CONSTANTS CONSTANTS */
    const val SOMETHING_WENT_WRONG_ERROR = "Oops! Something went wrong"


    /*  ERROR AND ALERT MESSAGES    */
    const val ERROR_NO_INTERNET_ALERT = "No Internet Connection"
    const val ERROR_ALERT = "Error"

    const val ERROR_MESSAGE = "Please Check your Internet Connection and Try Again."
    const val ERROR_ALERT_INVALID_TOKEN = "Invalid Token"
    const val ERROR_MESSAGE_INVALID_TOKEN = "Please Login and Try Again"
    const val ERROR_INVALID_TOKEN = "Invalid Token"

    /* ***************************************************************** */ /* PERMISSION ACCESS CONSTANTS */
    const val PARENT = 0
    const val CHILD = 1
    const val KEY_ALL_PERMISSIONS_ACCESS = 3
    const val KEY_SETTINGS_ACCESS = 2
    const val KEY_STORAGE_ACCESS = 1
    const val REQUEST_CODE_ASK_PERMISSIONS = 19945
    const val STUDENT = "1"
    const val TUTOR = "2"
    const val PERMISSION_ACCESS_STORAGE = "permission_storageaccess"

    /*  SHARED PREFERENCES CONSTANTS    */
    const val USER_ROLE_KEY = "user_role"
    const val KEY_CHECK_LOGIN = "check_login"
    const val KEY_ACCESS_TOKEN = "token"
    const val KEY_TOKEN_TYPE = "token_type"
    const val KEY_USER_TYPE = "user_type"

    const val KEY_USER_ID = "user_id"
    const val KEY_USER_NAME = "user_name"
    const val KEY_LOGIN_DATA = "login_data"
    const val KEY_USER_GENDER = "user_gender"
    const val KEY_USER_AGE = "user_age"
    const val KEY_USER_PROFILE = "user_profile"

    const val KEY_FIREBASE_UID = "firebase_uid"
    const val KEY_SEARCH = "search"
    const val KEY_USER_PIN = "user_pin"
    const val KEY_IS_PIN_ENTERED = "is_pin_entered"
    const val IN_APP_PERMISSION = "Need Permissions"
    const val PERMISSIONS_ACCESS_MESSAGE =
        "This app requires permissions to access your location and storage"

}