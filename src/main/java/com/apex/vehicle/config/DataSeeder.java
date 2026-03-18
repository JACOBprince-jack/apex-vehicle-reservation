package com.apex.vehicle.config;

import com.apex.vehicle.entity.Vehicle;
import com.apex.vehicle.entity.Vehicle.VehicleType;
import com.apex.vehicle.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Runs once at startup.
 * If the vehicles table is empty, it loads all 50 APEX vehicles so the
 * app works immediately without needing to run the SQL script manually.
 */
@Configuration
@RequiredArgsConstructor
@Slf4j
public class DataSeeder {

    private final VehicleRepository vehicleRepo;

    @Bean
    CommandLineRunner seedVehicles() {
        return args -> {
            if (vehicleRepo.count() > 0) {
                log.info("Vehicles already seeded — skipping.");
                return;
            }
            log.info("Seeding APEX vehicle fleet (50 vehicles)…");

            save("i01","Mahindra","Thar","car",2024,170,5,"Automatic","Diesel",900,6000,true,
                "https://images.unsplash.com/photo-1710225358761-4f5891df657d?q=80&w=464",
                "https://images.unsplash.com/photo-1710225358761-4f5891df657d?q=80&w=464");

            save("i02","Hyundai","i20","car",2024,170,7,"Automatic","Diesel",800,5950,true,
                "https://images.unsplash.com/photo-1519641471654-76ce0107ad1b?w=700&q=80",
                "https://www.v3cars.com/media/model-imgs/1665466646-02-Left-Front-Qtr.webp");

            save("i03","Tata","Nexon EV Max Long Range","car",2024,143,5,"Automatic","Electric",950,6500,true,
                "https://images.unsplash.com/photo-1593941707882-a56bbc8ba4d9?w=700&q=80",
                "https://media.fortuneindia.com/fortune-india/import/2023-09/c7560ff2-35ba-46ea-96ca-ae2a2fa86ef5/tata_nexon_ev.jpg?w=640");

            save("i04","Tata","Punch EV Empowered+","car",2024,122,5,"Automatic","Electric",900,6500,true,
                "https://images.unsplash.com/photo-1563720223185-11003d516935?w=700&q=80",
                "https://motoringworld.in/wp-content/uploads/2024/01/4-1.png");

            save("i05","Tata","Curvv EV Accomplished+","car",2024,167,5,"Automatic","Electric",950,6500,true,
                "https://images.unsplash.com/photo-1602777924212-89d3e1fc3e97?w=700&q=80",
                "https://media.zigcdn.com/media/content/2024/Jul/669a31b50a4a6.jpg?tr=w-420");

            save("i06","Mahindra","XUV700 AX7 Luxury","car",2024,200,7,"Automatic","Petrol",1150,7080,true,
                "https://images.unsplash.com/photo-1568605117036-5fe5e7bab0b7?w=700&q=80",
                "https://media.zigcdn.com/media/model/2025/Nov/model-extimg-981834496_600x400.jpg");

            save("i07","Mahindra","Thar Roxx 4XDRIVE","car",2024,175,5,"Automatic","Diesel",1312,6900,true,
                "https://images.unsplash.com/photo-1506015391300-4802dc74de2e?w=700&q=80",
                "https://images.hindustantimes.com/auto/img/2024/08/17/1600x900/Mahindra_Thar_Roxx_01_1723863061883_1723888572288.jpg");

            save("i08","Mahindra","Scorpio-N Z8 Ultimate","car",2024,175,7,"Automatic","Diesel",1200,6320,true,
                "https://images.unsplash.com/photo-1533473359331-0135ef1b58bf?w=700&q=80",
                "https://spn-sta.spinny.com/blog/20220627145950/Mahindra-Scorpio-N-header.jpg");

            save("i09","Mahindra","XEV 9e All Electric","car",2024,286,5,"Automatic","Electric",800,6000,true,
                "https://images.unsplash.com/photo-1555399718-4f2e64a42e13?w=700&q=80",
                "https://vandipranthan.com/wp-content/uploads/2025/01/abc-be-6.jpg");

            save("i10","Mahindra","BE 6e Performance EV","car",2024,228,5,"Automatic","Electric",1002,6020,false,
                "https://images.unsplash.com/photo-1560958089-b8a1929cea89?w=700&q=80",
                "https://content.carlelo.com/media/models/BE6/base/be-6-11.webp");

            save("i11","Maruti","Grand Vitara Alpha+ Hybrid","car",2024,103,5,"Automatic","Hybrid",1100,7400,true,
                "https://images.unsplash.com/photo-1580273916550-e323be2ae537?w=700&q=80",
                "https://imgd.aeplcdn.com/664x374/n/cw/ec/123185/grand-vitara-exterior-right-front-three-quarter-3.jpeg?isig=0&q=80");

            save("i12","Maruti","Invicto ZXi Plus","car",2024,116,7,"Automatic","Hybrid",1500,7870,true,
                "https://images.unsplash.com/photo-1494976388531-d1058494cdd8?w=700&q=80",
                "https://www.spinny.com/blog/wp-content/uploads/2024/07/Maruti-Suzuki-Invicto.webp");

            save("i13","Maruti","Fronx Alpha Turbo","car",2024,100,5,"Automatic","Petrol",1000,6500,true,
                "https://images.unsplash.com/photo-1609521263047-f8f205293f24?w=700&q=80",
                "https://imgd.aeplcdn.com/1920x1080/n/cw/ec/130591/fronx-exterior-right-front-three-quarter-108.jpeg?isig=0&q=80");

            save("i14","Hyundai","Creta EV Executive","car",2024,170,5,"Automatic","Electric",900,6000,true,
                "https://images.unsplash.com/photo-1532581140115-3e355d1ed1de?w=700&q=80",
                "https://www.hyundai.com/content/dam/hyundai/in/en/data/find-a-car/creta-electric/highlights/cretaevsmallimage1.jpg");

            save("i15","Hyundai","Ioniq 6 Premium","car",2024,229,5,"Automatic","Electric",1000,6000,true,
                "https://images.unsplash.com/photo-1603584173870-7f23fdae1b7a?w=700&q=80",
                "https://static.independent.co.uk/2025/05/06/15/57/Hyundai-Ioniq-5-N.png");

            save("i16","Hyundai","Tucson N-Line AWD","car",2024,180,5,"Automatic","Petrol",1100,6700,false,
                "https://images.unsplash.com/photo-1503376780353-7e6692767b70?w=700&q=80",
                "https://www.usnews.com/object/image/0000019a-3bfe-d7f7-adfb-3bfedde40000/2026-hyundai-tucson-front-angle-view-hma.jpg?size=responsive640&format=webp");

            save("i17","Kia","Seltos","car",2024,325,5,"Automatic","Petrol",950,6200,true,
                "https://images.unsplash.com/photo-1617788138017-80ad40651399?w=700&q=80",
                "https://static-cdn.cars24.com/prod/auto-news24-cms/Newsroom/2024/12/31/477ff882-d998-4a38-83c4-9b202cd2517b-2025-kia-seltos-gravity-edition-black-suv-design-kia.jpg");

            save("i18","Kia","Carnival Limousine","car",2024,200,8,"Automatic","Diesel",1400,7600,true,
                "https://images.unsplash.com/photo-1544636331-e26879cd4d9b?w=700&q=80",
                "https://hips.hearstapps.com/mtg-prod/65f7f75cfa6ceb0008e5c27b/2025-kia-carnival-minivan-reveal-3.jpg");

            // Motorcycles Indian
            save("im01","Royal Enfield","Super Meteor 650","motorcycle",2024,47,2,"Manual","Petrol",600,4000,true,
                "https://images.unsplash.com/photo-1449426468159-d96dbf08f19f?w=700&q=80",
                "https://imgd.aeplcdn.com/1280x720/n/cw/ec/210213/meteor-350-right-front-three-quarter-3.jpeg?isig=0");

            save("im02","Royal Enfield","Interceptor 650","motorcycle",2024,47,2,"Manual","Petrol",400,4050,true,
                "https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=700&q=80",
                "https://www.webbikeworld.com/wp-content/uploads/2023/08/2022-Royal-Enfield-Interceptor-650-Review-2.jpg");

            save("im03","Royal Enfield","Himalayan 450","motorcycle",2024,40,2,"Manual","Petrol",400,3000,true,
                "https://images.unsplash.com/photo-1615172282427-9a57ef2d142e?w=700&q=80",
                "https://akm-img-a-in.tosshub.com/businesstoday/images/story/202311/2nd0014_p-sixteen_nine.jpg?size=948:533");

            save("im04","Royal Enfield","Bullet 350 B55","motorcycle",2024,20,2,"Manual","Petrol",750,4500,true,
                "https://images.unsplash.com/photo-1568772585407-9f217745d51a?w=700&q=80",
                "https://cdn.motor1.com/images/mgl/9mNq8X/354:0:1080:1080/royal-enfield-bullet-350---black-gold.webp");

            save("im05","Royal Enfield","Hunter 350 Metro","motorcycle",2024,20,2,"Manual","Petrol",600,3500,true,
                "https://images.unsplash.com/photo-1517524008697-84bbe3c3fd98?w=700&q=80",
                "https://cdn.motor1.com/images/mgl/NGK3o2/s1/royal-enfield-hunter-350---old-joliet-prison.webp");

            save("im06","Jawa","42 FJ Perak","motorcycle",2024,30,2,"Manual","Petrol",600,3500,true,
                "https://images.unsplash.com/photo-1609630875171-b1321377ee65?w=700&q=80",
                "https://www.wheelsbingo.com/images/web-img/blogs/89859_bimg.webp");

            save("im07","Jawa","Perak Bobber Custom","motorcycle",2024,30,1,"Manual","Petrol",500,3085,false,
                "https://images.unsplash.com/photo-1568139235400-d84b3e70b07e?w=700&q=80",
                "https://images.overdrive.in/wp-content/uploads/2023/09/2023-Jawa-42-Bobber-900x506.jpg");

            save("im08","KTM","Duke 390","motorcycle",2024,45,2,"Manual","Petrol",500,4400,true,
                "https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=700&q=80",
                "https://5.imimg.com/data5/ED/XD/MY-38875342/ktm-duke.jpg");

            save("im09","KTM","Adventure 390","motorcycle",2024,46,2,"Manual","Petrol",482,4060,true,
                "https://images.unsplash.com/photo-1449426468159-d96dbf08f19f?w=700&q=80",
                "https://imgd.aeplcdn.com/1280x720/n/cw/ec/170715/390-adventure-2025-right-rear-three-quarter.jpeg?isig=0");

            save("im10","Hero","Mavrick 440","motorcycle",2024,27,2,"Manual","Petrol",402,4035,true,
                "https://images.unsplash.com/photo-1517524008697-84bbe3c3fd98?w=700&q=80",
                "https://gaadiwaadi.com/wp-content/uploads/2024/01/hero-mavrick-440-6.jpg");

            save("im11","Bajaj","Dominar 400 TS","motorcycle",2024,40,2,"Manual","Petrol",452,4500,true,
                "https://images.unsplash.com/photo-1568139235400-d84b3e70b07e?w=700&q=80",
                "https://sc0.blr1.cdn.digitaloceanspaces.com/article/116437-txtfhilqgi-1554097899.jpeg");

            save("im12","Bajaj","Pulsar NS400Z","motorcycle",2024,45,2,"Manual","Petrol",440,4085,true,
                "https://images.unsplash.com/photo-1609630875171-b1321377ee65?w=700&q=80",
                "https://motoringworld.in/wp-content/uploads/2024/05/Bajaj-Pulsar-NS400Z.jpg");

            // International Cars
            save("c01","BMW","M5 Competition","car",2024,625,5,"Automatic","Petrol",2000,9750,true,
                "https://images.unsplash.com/photo-1555215695-3004980ad54e?w=700&q=80",
                "https://images.unsplash.com/photo-1555215695-3004980ad54e?w=400&q=80");

            save("c02","Mercedes","AMG GT 63 S","car",2024,630,4,"Automatic","Petrol",4035,10060,true,
                "https://images.unsplash.com/photo-1618843479313-40f8afb4b4d8?w=700&q=80",
                "https://images.unsplash.com/photo-1618843479313-40f8afb4b4d8?w=400&q=80");

            save("c03","Mercedes","S-Class S680","car",2024,612,5,"Automatic","Petrol",4840,11000,true,
                "https://images.unsplash.com/photo-1606664515524-ed2f786a0bd6?w=700&q=80",
                "https://www.topgear.com/sites/default/files/2022/03/1-Mercedes-S-Class-plug-in.jpg");

            save("c04","Maybach","S680 Haute Voiture","car",2024,621,4,"Automatic","Petrol",5000,14000,true,
                "https://images.unsplash.com/photo-1552519507-da3b142c6e3d?w=700&q=80",
                "https://media.zigcdn.com/media/model/2022/Jan/front-1-4-left-486137542_930x620.jpg");

            save("c05","Range Rover","Evoque","car",2024,530,5,"Automatic","Diesel",4900,10000,true,
                "https://images.unsplash.com/photo-1606016159991-dfe4f2746ad5?w=700&q=80",
                "https://images.overdrive.in/wp-content/uploads/2024/01/Untitled-design-96-900x506.png");

            save("c06","Range Rover","Defender V8","car",2024,518,5,"Automatic","Petrol",5000,9900,true,
                "https://images.unsplash.com/photo-1619551734325-81176a53e6f9?w=700&q=80",
                "https://img.autocarindia.com/ExtraImages/20250528071638_2025%20Defender%20front%20quarter%20.jpg?w=728&q=75");

            save("c07","Porsche","911 GT3 RS","car",2024,518,2,"PDK","Petrol",5500,13500,true,
                "https://images.unsplash.com/photo-1547245324-d777c6f05e80?w=700&q=80",
                "https://www.thedrive.com/wp-content/uploads/2023/08/11/porsche-carrera-T-review-lead.jpg?quality=85");

            save("c08","Lamborghini","Huracán EVO","car",2024,630,2,"LDF","Petrol",6000,15000,true,
                "https://images.unsplash.com/photo-1511919884226-fd3cad34687c?w=700&q=80",
                "https://images.unsplash.com/photo-1511919884226-fd3cad34687c?w=400&q=80");

            save("c09","Ferrari","Roma Spider","car",2024,620,2,"DCT","Petrol",6500,12700,false,
                "https://images.unsplash.com/photo-1592198084033-aade902d1aae?w=700&q=80",
                "https://images.unsplash.com/photo-1592198084033-aade902d1aae?w=400&q=80");

            save("c10","Jaguar","F-Pace R","car",2024,575,2,"Automatic","Petrol",5000,9020,true,
                "https://images.unsplash.com/photo-1614162692292-7ac56d7f7f1e?w=700&q=80",
                "https://images.overdrive.in/wp-content/uploads/2021/04/2021-jaguar-f-pace-booking-02.jpg");

            save("c11","BMW","X7 M60i","car",2024,530,7,"Automatic","Petrol",6515,9820,true,
                "https://images.unsplash.com/photo-1503376780353-7e6692767b70?w=700&q=80",
                "https://hips.hearstapps.com/hmg-prod/images/2023-bmw-x7-xdrive-40i134-641c5b3ca7165.jpg");

            save("c12","Mercedes","GLE 63 AMG","car",2024,603,5,"Automatic","Petrol",8025,13080,false,
                "https://images.unsplash.com/photo-1553440569-bcc63803a83d?w=700&q=80",
                "https://images.unsplash.com/photo-1553440569-bcc63803a83d?w=400&q=80");

            // International Motorcycles
            save("m01","Triumph","Speed Triple 1200 RS","motorcycle",2024,180,1,"Manual","Petrol",2500,6000,true,
                "https://images.unsplash.com/photo-1609630875171-b1321377ee65?w=700&q=80",
                "https://gen-performance.com/cdn/shop/files/TRIUMPH_SPEED_TRIPLE_1200_RS_GEN_PERFORMANCE.webp?v=1733768115&width=3840");

            save("m02","Triumph","Tiger 900 GT Pro","motorcycle",2024,95,2,"Manual","Petrol",3000,7500,true,
                "https://images.unsplash.com/photo-1568139235400-d84b3e70b07e?w=700&q=80",
                "https://imgd.aeplcdn.com/664x374/n/cw/ec/121719/tiger-1200-right-front-three-quarter.jpeg?isig=0&q=80");

            save("m03","Harley-Davidson","Fat Boy 114","motorcycle",2024,100,2,"Manual","Petrol",3500,7460,true,
                "https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=700&q=80",
                "https://www.carandbike.com/_next/image?url=https://images.carandbike.com/cms/articles/2025/5/3217006/Limited_Run_Harley_Davidson_Fat_Boy_Gray_Ghost_Revealed_61654bdfe9.jpg&w=640&q=90");

            save("m04","Harley-Davidson","SPORTSTAR","motorcycle",2024,115,2,"Manual","Petrol",3000,6000,true,
                "https://images.unsplash.com/photo-1517524008697-84bbe3c3fd98?w=700&q=80",
                "https://images.firstpost.com/wp-content/uploads/2021/12/harley-davidson-sportster-s-launched-india-15-51-lakh-price-india-bike-week-2021-1.jpg");

            save("m05","Suzuki","Hayabusa","motorcycle",2024,215,1,"Manual","Petrol",3500,6800,true,
                "https://images.unsplash.com/photo-1615172282427-9a57ef2d142e?w=700&q=80",
                "https://www.globalsuzuki.com/motorcycle/smgs/products/2026hayabusa/img/main_ph.jpg");

            save("m06","Kawasaki","Ninja ZX-10R","motorcycle",2024,203,2,"Manual","Petrol",3800,5900,true,
                "https://images.unsplash.com/photo-1568772585407-9f217745d51a?w=700&q=80",
                "https://i.pinimg.com/736x/38/7d/e5/387de5f08e628ac96e481fcd795e13c2.jpg");

            save("m07","Honda","CB1000R Black Edition","motorcycle",2024,145,2,"Manual","Petrol",2900,4500,true,
                "https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=700&q=80",
                "https://www.cycleworld.com/resizer/Z28JtTdfPyZE-fSZzPvHYeFz91E=/arc-photo-octane/arc3-prod/public/5B4DWDBSWPWNHB6FUQCVR2553E.jpg");

            save("m08","Triumph","Bonneville T120","motorcycle",2024,80,2,"Manual","Petrol",2900,5000,false,
                "https://images.unsplash.com/photo-1449426468159-d96dbf08f19f?w=700&q=80",
                "https://media.triumphmotorcycles.co.uk/image/upload/f_auto/q_auto:eco/sitecoremedialibrary/media-library/images/motorcycles/modern-classics/my21/t120/family%20page%20images/t120-family-timeline-slide-style-1920x780.jpg");

            log.info("✅ Seeded 50 vehicles successfully.");
        };
    }

    private void save(String id, String make, String model, String type,
                      int year, int hp, int seats, String trans, String fuel,
                      int ph, int pd, boolean avail, String img, String thumb) {
        Vehicle v = new Vehicle();
        v.setId(id);
        v.setMake(make);
        v.setModel(model);
        v.setType(VehicleType.valueOf(type));
        v.setYear(year);
        v.setHorsepower(hp);
        v.setSeats(seats);
        v.setTransmission(trans);
        v.setFuel(fuel);
        v.setPricePerHour(ph);
        v.setPricePerDay(pd);
        v.setAvailable(avail);
        v.setImageUrl(img);
        v.setThumbUrl(thumb);
        vehicleRepo.save(v);
    }
}
