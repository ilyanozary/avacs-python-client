package defpackage;

import javax.microedition.location.Criteria;
import javax.microedition.location.LocationProvider;
import javax.microedition.location.QualifiedCoordinates;

/* loaded from: avacs.jar:ah.class */
public class ah extends am implements Runnable {
    private int E;
    private int F;
    private int G;
    private String g = "";

    @Override // defpackage.am
    public final void a() {
        String[] strArrA = b.a(this.f101d, f119a);
        this.g = strArrA[0];
        this.E = Integer.parseInt(strArrA[1]);
        this.F = Integer.parseInt(strArrA[2]);
        this.G = Integer.parseInt(strArrA[3]);
        new Thread(this).start();
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            Criteria criteria = new Criteria();
            if (this.G > 0) {
                criteria.setPreferredResponseTime(this.G);
            }
            if (this.E > 0) {
                criteria.setHorizontalAccuracy(this.E);
            }
            if (this.F > 0) {
                criteria.setAltitudeRequired(true);
                criteria.setVerticalAccuracy(this.F);
            }
            LocationProvider locationProvider = LocationProvider.getInstance(criteria);
            LocationProvider locationProvider2 = locationProvider;
            if (locationProvider == null) {
                locationProvider2 = LocationProvider.getInstance(new Criteria());
            }
            QualifiedCoordinates qualifiedCoordinates = locationProvider2.getLocation(310).getQualifiedCoordinates();
            if (qualifiedCoordinates != null) {
                double latitude = qualifiedCoordinates.getLatitude();
                double longitude = qualifiedCoordinates.getLongitude();
                float altitude = qualifiedCoordinates.getAltitude();
                float horizontalAccuracy = qualifiedCoordinates.getHorizontalAccuracy();
                this.a.a(this.f89a, this.h, new StringBuffer().append(this.g).append(f122d).append(latitude).append(f119a).append(longitude).append(f119a).append(altitude).append(f119a).append(horizontalAccuracy).append(f119a).append(qualifiedCoordinates.getVerticalAccuracy()).toString(), null, this);
            }
        } catch (Exception e) {
            this.a.a(this.f89a, -117, b.m26a(new StringBuffer().append("").append(e).append(". ").append(e.getMessage()).toString(), f121c), null, this);
        }
    }
}
