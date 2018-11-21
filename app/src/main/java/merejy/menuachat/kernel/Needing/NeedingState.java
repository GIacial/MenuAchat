package merejy.menuachat.kernel.Needing;

public enum NeedingState {
    NEEDING_STATE_TAKE , NEEDING_STATE_AT_HOME , NEEDING_STATE_NONE;

    public static void configNeeding (NeedingInterface n , NeedingState state){
        switch (state){
            case NEEDING_STATE_TAKE:
                n.setTake(true);
                break;
            case NEEDING_STATE_AT_HOME:
                n.setAtHome(true);
                break;
                default: break;
        }
    }

    public static NeedingState getConfigNeeding ( NeedingInterface n){
        if(n.isAtHome()){
            return NEEDING_STATE_AT_HOME;
        }
        if(n.isTake()){
            return NEEDING_STATE_TAKE;
        }
        return NEEDING_STATE_NONE;
    }
}
