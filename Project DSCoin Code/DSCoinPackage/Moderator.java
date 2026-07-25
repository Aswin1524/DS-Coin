package DSCoinPackage;

import HelperClasses.*;
public class Moderator{

  public void initializeDSCoin(DSCoin_Honest DSObj, int coinCount) {
    Members[] memberlst = DSObj.memberlist;
    int trcount = DSObj.bChain.tr_count;
    int n = memberlst.length;
    String coin = "100000";
    int i = 0; int k=0;
    while(i<coinCount){
        int j = 0;
        Transaction[] tarray = new Transaction[trcount];
        while(j<tarray.length){
            Transaction t = new Transaction();
            t.coinID = coin;
            Members obj = new Members();
            obj.UID = "Moderator";
            t.Source = obj;
            t.Destination = memberlst[k % memberlst.length];
            t.coinsrc_block=null;
            tarray[j]=t;
            k=k+1;
            j=j+1;
            coin = String.valueOf(Integer.valueOf(coin)+1);
        }
        TransactionBlock tB = new TransactionBlock(tarray);
        DSObj.bChain.InsertBlock_Honest(tB);
        for(int c=0; c<tarray.length; c++){
            Transaction temp = tarray[c];
            Members member = temp.Destination;
            Pair<String, TransactionBlock> p = new Pair<String, TransactionBlock>(temp.coinID, tB);
            member.mycoins.add(p);
        }
        i=i+trcount;
    }
    DSObj.latestCoinID = String.valueOf(Integer.valueOf(coin)-1);
  }
    
  public void initializeDSCoin(DSCoin_Malicious DSObj, int coinCount) {
    CRF obj1 = new CRF(64);
    Members[] memberlst = DSObj.memberlist;
    int trcount = DSObj.bChain.tr_count;
    int n = memberlst.length;
    String coin = "100000";
    int i = 0; int k=0;
    while(i<coinCount){
        int j = 0;
        Transaction[] tarray = new Transaction[trcount];
        while(j<tarray.length){
            Transaction t = new Transaction();
            t.coinID = coin;
            Members obj = new Members();
            obj.UID = "Moderator";
            t.Source = obj;
            t.Destination = memberlst[k % memberlst.length];
            t.coinsrc_block=null;
            tarray[j]=t;
            k=k+1;
            j=j+1;
            coin = String.valueOf(Integer.valueOf(coin)+1);
        }
        TransactionBlock tB = new TransactionBlock(tarray);
        for(int c=0; c<tarray.length; c++){
            Transaction temp = tarray[c];
            Members member = temp.Destination;
            Pair<String, TransactionBlock> p = new Pair<String, TransactionBlock>(temp.coinID, tB);
            member.mycoins.add(p);
        }
        if(DSObj.bChain.lastBlocksList[0]==null){
            for(long l=1000000000L; l<=9999999999L; l++){
                if(obj1.Fn("DSCoin" + "#" + tB.trsummary + "#" + String.valueOf(l)).substring(0,4).equals("0000")){
                    tB.nonce=String.valueOf(l);
                    tB.dgst=obj1.Fn("DSCoin" + "#" + tB.trsummary + "#" + String.valueOf(l));
                    break;
                }
            }    
            DSObj.bChain.lastBlocksList[0]=tB;
        }
        else{
            DSObj.bChain.InsertBlock_Malicious(tB);
        }
        i=i+trcount;
    }
    DSObj.latestCoinID = String.valueOf(Integer.valueOf(coin)-1);   
  }

}